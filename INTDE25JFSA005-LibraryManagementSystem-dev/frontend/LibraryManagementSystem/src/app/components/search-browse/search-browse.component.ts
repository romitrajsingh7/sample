import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { BookService } from '../../services/book.service';
import { TransactionsService } from '../../services/transactions.service';
import { Book } from '../../models/book.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-member-browse',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './search-browse.component.html',
  styleUrls: ['./search-browse.component.css']
})
export class SearchBrowseComponent implements OnInit {
  books: Book[] = [];
  genreMap: { [genre: string]: Book[] } = {};
  searchField: 'all' | 'title' | 'author' | 'genre' = 'all';
  searchTerm = '';
  sidebarOpen = true;
  memberName = '';
  memberId!: number;
  customAlertMessage: string | null = null;

  constructor(
    private bookSvc: BookService,
    private txnSvc: TransactionsService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.getProfile().subscribe({
      next: profile => {
        this.memberName = profile?.name || 'Guest';
        this.memberId = profile.id;
        this.loadBooks();
      },
      error: err => console.error('Failed to load profile:', err)
    });
  }

  loadBooks(): void {
    this.bookSvc.getBooks().subscribe({
      next: (list) => {
        this.books = list;
        this.groupBooksByGenre();
      },
      error: (e) => console.error(e)
    });
  }

  groupBooksByGenre(): void {
    const t = this.searchTerm.toLowerCase();
    const filtered = this.books.filter(b => {
      switch (this.searchField) {
        case 'title': return b.title.toLowerCase().includes(t);
        case 'author': return b.author.toLowerCase().includes(t);
        case 'genre': return b.genre.toLowerCase().includes(t);
        default:
          return b.title.toLowerCase().includes(t) || b.author.toLowerCase().includes(t) || b.genre.toLowerCase().includes(t);
      }
    });

    this.genreMap = filtered.reduce((acc, book) => {
      const genre = book.genre;
      if (!acc[genre]) acc[genre] = [];
      acc[genre].push(book);
      return acc;
    }, {} as { [genre: string]: Book[] });
  }

  borrow(book: Book): void {
    const dto = { memberId: this.memberId, bookId: book.bookId };
    this.txnSvc.borrowBook(dto).subscribe({
      next: () => {
        this.customAlertMessage = `Borrowed "${book.title}" successfully!`;
        this.loadBooks();
      },
      error: err => {
        const msg = err.error?.message || err.message || 'Unknown error occurred.';
        this.customAlertMessage = `Borrow denied: ${msg}`;
      }
    });
  }

  closeAlert(): void {
    this.customAlertMessage = null;
  }

  toggleSidebar(): void {
    this.sidebarOpen = !this.sidebarOpen;
  }

  navigate(path: string): void {
    this.router.navigateByUrl(path);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
