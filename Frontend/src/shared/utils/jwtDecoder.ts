export interface DecodedToken {
    userId: number;
    email: string;
    role: string;
    fullName: string;
    sub: string;
    iat: number;
    exp: number;
}

export const jwtDecoder = {
    /**
     * Decodifica el token JWT y extrae los claims
     */
    decode(token: string): DecodedToken | null {
        try {
            // JWT tiene formato: header.payload.signature
            const parts = token.split('.');
            if (parts.length !== 3) {
                return null;
            }

            // Decodificar el payload (segunda parte)
            const payload = parts[1];
            const decodedPayload = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
            const parsed = JSON.parse(decodedPayload);

            return parsed as DecodedToken;
        } catch (error) {
            console.error('Error decoding JWT:', error);
            return null;
        }
    },

    /**
     * Extrae el rol del token
     */
    getRole(token: string): string | null {
        const decoded = this.decode(token);
        return decoded?.role || null;
    },

    /**
     * Verifica si el token ha expirado
     */
    isExpired(token: string): boolean {
        const decoded = this.decode(token);
        if (!decoded || !decoded.exp) {
            return true;
        }

        const currentTime = Date.now() / 1000;
        return decoded.exp < currentTime;
    },

    /**
     * Verifica si el usuario es ADMIN
     */
    isAdmin(token: string): boolean {
        const role = this.getRole(token);
        return role === 'ROLE_ADMIN';
    }
};