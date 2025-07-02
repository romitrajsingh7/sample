import { Injectable} from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { RegisterRequestDto } from "../models/user.model";
import { AuthResponseDto } from "../models/auth-response.model";
import { MemberProfile } from "../models/member-profile";
@Injectable({providedIn: 'root'})
export class AuthService{
    private baseUrl = 'http://localhost:8080/api/auth';

    constructor(private http: HttpClient){}

    register(dto: RegisterRequestDto): Observable<string>{
        return this.http.post<string>(`${this.baseUrl}/register`, dto,{
            responseType: 'text' as 'json'
        });
    }
    login(credentials: { email: string; password: string }): Observable<AuthResponseDto> {
      return this.http.post<AuthResponseDto>(`${this.baseUrl}/login`, credentials);
    }
  
    saveToken(token: string, role: string) {
      localStorage.setItem('jwtToken', token);
      localStorage.setItem('role', role);
    }
  
    getToken(): string | null {
      return localStorage.getItem('jwtToken');
    }
  
    getUserRole(): string | null {
      return localStorage.getItem('role');
    }
  
    logout() {
      const token = localStorage.getItem('jwtToken');
      const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('role');
      return this.http.post<string>(`${this.baseUrl}/logout`, {}, { headers });

    }

    getProfile(): Observable<MemberProfile> {
  const token = localStorage.getItem('jwtToken'); // or sessionStorage
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });

  return this.http.get<MemberProfile>(`${this.baseUrl}/profile`, { headers });
}

  updateProfile(updatedProfile: Partial<MemberProfile>): Observable<MemberProfile> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.put<MemberProfile>(`${this.baseUrl}/profile`, updatedProfile, { headers });
  }
}