import { authService } from './authService';
import type { AuditLog, AuditStatistics } from '../types/audit.types';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

export const auditService = {
    /**
     * Obtiene todos los logs de auditoría
     */
    async getAllLogs(): Promise<AuditLog[]> {
        const token = authService.getToken();
        if (!token) {
            throw new Error('No autorizado');
        }

        const response = await fetch(`${API_BASE_URL}/audit/logs`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Error al obtener logs de auditoría');
        }

        return response.json();
    },

    /**
     * Obtiene estadísticas de auditoría
     */
    async getStatistics(): Promise<AuditStatistics> {
        const token = authService.getToken();
        if (!token) {
            throw new Error('No autorizado');
        }

        const response = await fetch(`${API_BASE_URL}/audit/stats`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Error al obtener estadísticas');
        }

        return response.json();
    },

    /**
     * Obtiene logs por tipo de evento
     */
    async getLogsByEventType(eventType: string): Promise<AuditLog[]> {
        const token = authService.getToken();
        if (!token) {
            throw new Error('No autorizado');
        }

        const response = await fetch(`${API_BASE_URL}/audit/logs/event-type/${eventType}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Error al obtener logs por tipo');
        }

        return response.json();
    },

    /**
     * Obtiene logs por usuario
     */
    async getLogsByUserId(userId: number): Promise<AuditLog[]> {
        const token = authService.getToken();
        if (!token) {
            throw new Error('No autorizado');
        }

        const response = await fetch(`${API_BASE_URL}/audit/logs/user/${userId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Error al obtener logs por usuario');
        }

        return response.json();
    }
};