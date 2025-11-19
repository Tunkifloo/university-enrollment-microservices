import { create } from 'zustand';
import { auditService } from '../../../shared/services/auditService';
import type { AuditLog, AuditStatistics } from '../../../shared/types/audit.types';

interface AuditState {
    logs: AuditLog[];
    statistics: AuditStatistics | null;
    isLoading: boolean;
    error: string | null;

    fetchLogs: () => Promise<void>;
    fetchStatistics: () => Promise<void>;
    fetchLogsByEventType: (eventType: string) => Promise<void>;
    clearError: () => void;
}

export const useAuditStore = create<AuditState>((set) => ({
    logs: [],
    statistics: null,
    isLoading: false,
    error: null,

    fetchLogs: async () => {
        set({ isLoading: true, error: null });
        try {
            const logs = await auditService.getAllLogs();
            set({ logs, isLoading: false });
        } catch (error) {
            set({
                error: error instanceof Error ? error.message : 'Error al cargar logs',
                isLoading: false
            });
        }
    },

    fetchStatistics: async () => {
        set({ isLoading: true, error: null });
        try {
            const statistics = await auditService.getStatistics();
            set({ statistics, isLoading: false });
        } catch (error) {
            set({
                error: error instanceof Error ? error.message : 'Error al cargar estadísticas',
                isLoading: false
            });
        }
    },

    fetchLogsByEventType: async (eventType: string) => {
        set({ isLoading: true, error: null });
        try {
            const logs = await auditService.getLogsByEventType(eventType);
            set({ logs, isLoading: false });
        } catch (error) {
            set({
                error: error instanceof Error ? error.message : 'Error al filtrar logs',
                isLoading: false
            });
        }
    },

    clearError: () => set({ error: null }),
}));