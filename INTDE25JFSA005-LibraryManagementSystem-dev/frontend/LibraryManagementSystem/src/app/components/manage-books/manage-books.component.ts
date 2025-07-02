// src/app/components/admin-manage-books/admin-manage-books.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { BookService } from '../../services/book.service';
import { Book } from '../../models/book.model';
import { AuthService } from '../../services/auth.service';
@Component({
  selector: 'app-manage-books',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './manage-books.component.html',
  styleUrls: ['./manage-books.component.css'],
})
export class ManageBooksComponent implements OnInit {
  sidebarOpen = true;
  adminName = '';

  books: Book[] = [];
  filtered: Book[] = [];
  genreGroups: { [genre: string]: Book[] } = {};
  searchField: 'all' | 'bookId' | 'title' | 'author' | 'genre' | 'yearPublished' = 'all';
  searchTerm = '';


  showAdd = false;
  newBook: Partial<Book> = {
    title: '',
    author: '',
    genre: '',
    isbn: '',
    yearPublished: new Date().getFullYear(),
    availableCopies: 1,
  };

  editingId: number | null = null;
  editAvailable = 0;
  newCoverFile: File | null = null;
  newCoverPreview = '';
  editCoverFile: File | null = null;


  constructor(private bookSvc: BookService, private router: Router,private authService: AuthService) {}

  ngOnInit() {
    this.adminName = localStorage.getItem('userName') || 'Admin';
    this.loadBooks();
  }
onNewCoverSelected(evt: any) {
    const file: File = evt.target.files[0];
    if (file) {
      this.newCoverFile = file;
      const reader = new FileReader();
      reader.onload = () => this.newCoverPreview = reader.result as string;
      reader.readAsDataURL(file);
    }
  }
  loadBooks() {
    this.bookSvc.getBooks().subscribe({
      next: (list) => {
        this.books = list;
        this.applyFilter();
      },
      error: (e) => console.error(e),
    });
  }

  applyFilter() {
    const t = this.searchTerm.toLowerCase();
    const filteredBooks = this.books.filter(b => {
      switch (this.searchField) {
        case 'bookId': return b.bookId.toString().includes(t);
        case 'title': return b.title.toLowerCase().includes(t);
        case 'author': return b.author.toLowerCase().includes(t);
        case 'genre': return b.genre.toLowerCase().includes(t);
        case 'yearPublished': return b.yearPublished.toString().includes(t);
        default:
          return b.bookId.toString().includes(t)
            || b.title.toLowerCase().includes(t)
            || b.author.toLowerCase().includes(t)
            || b.genre.toLowerCase().includes(t)
            || b.yearPublished.toString().includes(t);
      }
    });
  this.filtered = filteredBooks;

    // Group by genre
    this.genreGroups = {};
    for (const book of filteredBooks) {
      const genre = book.genre || 'Unknown';
      if (!this.genreGroups[genre]) {
        this.genreGroups[genre] = [];
      }
      this.genreGroups[genre].push(book);
    }
  }
  
  saveNew() {
    const create = () => {
      this.bookSvc.addBook(this.newBook).subscribe(() => {
        this.resetNewForm();
        this.loadBooks();
      });
    };

    if (this.newCoverFile) {
      this.bookSvc.uploadImage(this.newCoverFile).subscribe(url => {
        this.newBook.imageUrl = url;
        create();
      });
    } else {
      create();
    }
  }

  resetNewForm() {
    this.showAdd = false;
    this.newBook = {
      title: '', author: '', genre: '', isbn: '',
      yearPublished: new Date().getFullYear(),
      availableCopies: 1
    };
    this.newCoverFile = null;
    this.newCoverPreview = '';
  }

  startEdit(b: Book) {
    this.editingId = b.bookId;
    this.editAvailable = b.availableCopies;
  }
  cancelEdit() {
    this.editingId = null;
  }
  updateCopies(id: number) {
    this.bookSvc.updateCopies(id, this.editAvailable).subscribe({
      next: () => {
        this.cancelEdit();
        this.loadBooks();
      },
      error: (e) => console.error(e),
    });
  }

  customAlertMessage: string | null = null
  bookToDelete: Book | null = null;


  
  deleteBook(b: Book) {
    this.customAlertMessage = `Delete book “${b.title}”?`;
    this.bookToDelete=b;

  }
  
  closeAlert(): void {
    this.customAlertMessage = null;
    this.bookToDelete=null;
  }

  
  
  confirmDelete() {
    if (this.bookToDelete) {
      this.bookSvc.deleteBook(this.bookToDelete.bookId).subscribe({
        next: () => this.loadBooks(),
        error: (e) => console.error(e),
      });
      this.closeAlert();
    }
  }
  
  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }
  navigate(path: string): void {
    this.router.navigateByUrl(path);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']); 
  }
}
