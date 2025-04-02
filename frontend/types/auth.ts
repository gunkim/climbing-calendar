export interface User {
    id: string
    email: string
    name: string
    profileImage?: string
}

export interface AuthState {
    user: User | null
    isAuthenticated: boolean
}

