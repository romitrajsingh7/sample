import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { MemberService } from '../../services/member.service';
import { Member } from '../../models/member.model';
import { MembershipStatus } from '../../models/membership-status.model';
import { AuthService } from '../../services/auth.service';
@Component({
  selector: 'app-manage-members',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './manage-members.component.html',
  styleUrls: ['./manage-members.component.css']
})
export class ManageMembersComponent implements OnInit {
  sidebarOpen = true;
  adminName = '';

  members: Member[] = [];
  filtered: Member[] = [];
  searchTerm = '';
  searchField: 'all' | 'memberId' | 'name' | 'email' = 'all';

  // Editing state
  editingMemberId: number | null = null;
  editData: Partial<Member> = {};
  statusOptions: MembershipStatus[] = ['ACTIVE', 'INACTIVE'];

  constructor(
    private memberService: MemberService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.adminName = localStorage.getItem('userName') || 'Admin';
    this.loadMembers();
  }
  navigate(path: string): void {
    this.router.navigateByUrl(path);
  }
  loadMembers(): void {
    this.memberService.getAllMembers().subscribe({
      next: list => {
        this.members = list.filter(mx=> mx.userRole === 'MEMBER');
        this.applyFilter();
      }, error: err => console.error(err)
    });
  }

applyFilter() {
    const t = this.searchTerm.toLowerCase();
    this.filtered = this.members.filter(b => {
      switch (this.searchField) {
        case 'memberId': return b.memberId.toString().includes(t);
        case 'name': return b.name.toLowerCase().includes(t);
        case 'email': return b.email.toLowerCase().includes(t);
        default:
          return b.memberId.toString().includes(t)
            || b.name.toLowerCase().includes(t)
            || b.email.toLowerCase().includes(t)
      }
    });
  }
  startEdit(member: Member): void {
    this.editingMemberId = member.memberId;
    this.editData = { name: member.name, phone: member.phone,address:member.address  };
  }

  cancelEdit(): void {
    this.editingMemberId = null;
    this.editData = {};
  }

  saveEdit(memberId: number): void {
    this.memberService.updateMember(memberId, this.editData).subscribe({
      next: () => {
        this.loadMembers();
        this.cancelEdit();
      }, error: err => console.error(err)
    });
  }

  changeStatus(memberId: number, status: MembershipStatus): void {
    this.memberService.updateMembershipStatus(memberId, status).subscribe({
      next: () => this.loadMembers(),
      error: err => console.error(err)
    });
  }

  // deleteMember(member: Member): void {
  //   if (confirm(`Delete member ${member.name}?`)) {
      // this.memberService.deleteMember(member.memberId)
      //   .subscribe({ next: () => this.loadMembers(), error: err => console.error(err) });
  //   }
  // }

  customAlertMessage: string | null = null
  memberToDelete: Member | null = null;
  
  
    
    deleteMember(member: Member) {
      this.customAlertMessage = `Delete member ${member.name}?`;
      this.memberToDelete=member;
  
    }
    
    closeAlert(): void {
      this.customAlertMessage = null;
      this.memberToDelete=null;
    }
  
    
    
    confirmDelete() {
      if (this.memberToDelete) {
        this.memberService.deleteMember(this.memberToDelete.memberId).subscribe({ 
          next: () => this.loadMembers(), error: err => console.error(err) });
        this.closeAlert();
      }
    }




  toggleSidebar(): void { this.sidebarOpen = !this.sidebarOpen; }
  logout() {
    this.authService.logout();
    this.router.navigate(['/login']); 
  }
}