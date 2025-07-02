import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { TransactionsService } from '../../services/transactions.service';
import { BorrowingTransaction } from '../../models/borrowing-transaction.model';
import { AuthService } from '../../services/auth.service';
import { Fine } from '../../models/fine.model';
import { FinesService } from '../../services/fine.service';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-overdue',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './overdue-fine.component.html',
  styleUrls: ['./overdue-fine.component.css']
})
export class OverdueFineComponent implements OnInit {
  sidebarOpen = true;
  memberName = '';
  overdueTransactions: BorrowingTransaction[] = [];
  readonly loanPeriodDays = 14; 
  //memberId!: number;
  fines: Fine[] = [];
  selectedTab: 'overdue' | 'fine' = 'overdue';   // ⬅️ default view


  constructor(
    private http: HttpClient,
    private txnService: TransactionsService,
    private router: Router,
    private authService: AuthService,
    private finesService: FinesService
  ) {}

  ngOnInit(): void {
    this.authService.getProfile().subscribe({
      next: profile => {
        this.memberName = profile?.name || 'Admin';
        this.loadOverdue();
        //this.memberId = profile.id;
        this.loadFines();
      },
      error: err => console.error('Failed to load profile:', err)
    });
  }
  selectTab(tab: 'overdue' | 'fine'): void {
    this.selectedTab = tab;
  }
  toggleSidebar(): void {
    this.sidebarOpen = !this.sidebarOpen;
  }

  navigate(path: string): void {
    this.router.navigateByUrl(path);
  }

  loadOverdue(): void {
    this.txnService.getOverDueTransactions().subscribe({
      next: all => {
        const today = new Date();
        this.overdueTransactions = all.filter(tx => {
          if (tx.returnDate) return false;
          const borrow = new Date(tx.borrowDate);
          const due = new Date(borrow);
          due.setDate(due.getDate() + this.loanPeriodDays);
          return due < today;
        });
      },
      error: err => console.error('Error loading transactions', err)
    });
  }

  getDueDate(tx: BorrowingTransaction): Date {
    const borrow = new Date(tx.borrowDate);
    const due = new Date(borrow);
    due.setDate(due.getDate() + this.loanPeriodDays);
    return due;
  }

  daysOverdue(tx: BorrowingTransaction): number {
    const due = this.getDueDate(tx);
    const diff = new Date().getTime() - due.getTime();
    return Math.floor(diff / (1000 * 60 * 60 * 24));
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']); 
  }
  loadFines(): void {
    this.finesService.getPendingFines().subscribe({
      next: data => this.fines = data,
      error: err => console.error('Failed to load fines', err)
    });
  }
  calculateFine(): void{
    this.http.post('http://localhost:8080/api/fines/calculate',null).subscribe({
      next: () => {
        this.loadFines();
        this.loadOverdue();
      },
      error: err => console.error('failed', err)
    })
  } 
  }
