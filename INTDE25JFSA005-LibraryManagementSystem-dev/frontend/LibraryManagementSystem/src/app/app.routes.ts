import { Routes } from '@angular/router';
import { RegisterComponent }           from './components/register/register.component';
import { LoginComponent }              from './components/login/login.component';
import { AdminDashboardComponent }     from './components/admin-dashboard/admin-dashboard.component';
import { ProfileComponent }            from './components/profile/profile.component';
import { BorrowTransactionsComponent } from './components/borrow-transactions/borrow-transactions.component';
import { OverdueComponent }            from './components/overdue/overdue.component';
import { AdminGuard }                  from './guards/admin.guard';
import { TransactionsComponent } from './components/transactions/transactions.component'; 
import { ManageMembersComponent } from './components/manage-members/manage-members.component';
import { ManageBooksComponent } from './components/manage-books/manage-books.component';
import { MemberDashboardComponent } from './components/member-dashboard/member-dashboard.component';
import { OverdueFineComponent } from './components/overdue-fine/overdue-fine.component';
import { SearchBrowseComponent } from './components/search-browse/search-browse.component';
import { MemberGuard } from './guards/member.guard';
export const routes: Routes = [
  // Default redirect to login
  { path: '', redirectTo: 'login', pathMatch: 'full' },
 
  // Public routes
  { path: 'register', component: RegisterComponent },
  { path: 'login',    component: LoginComponent },
 
  // Member-only routes
  { path: 'dashboard',          component: MemberDashboardComponent, canActivate:[MemberGuard] },
  { path: 'profile',            component: ProfileComponent ,canActivate:[MemberGuard]},
  { path: 'borrow-transactions', component: BorrowTransactionsComponent, canActivate:[MemberGuard] },
  { path: 'overdue',            component: OverdueComponent, canActivate:[MemberGuard]},
  { path: 'search & browse',     component: SearchBrowseComponent, canActivate:[MemberGuard] },

  // Admin-only route
  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'overduefine',
    component: OverdueFineComponent,
    canActivate: [AdminGuard]
  },
  {path:'manage-members', component:ManageMembersComponent,canActivate: [AdminGuard]},
  {path:'manage-books',component:ManageBooksComponent, canActivate: [AdminGuard]},
  {path:'transactions',component:TransactionsComponent,canActivate: [AdminGuard]},
 

];