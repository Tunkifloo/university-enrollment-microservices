import { Calendar, User, FileText, Tag } from 'lucide-react';
import type { AuditLog } from '../../../shared/types/audit.types';
import { EventType } from '../../../shared/types/audit.types';

interface AuditLogsTableProps {
    logs: AuditLog[];
}

export const AuditLogsTable = ({ logs }: AuditLogsTableProps) => {
    const formatDate = (dateStr: string) => {
        return new Date(dateStr).toLocaleString('es-PE', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });
    };

    const getEventTypeBadge = (eventType: string) => {
        const colors: Record<string, string> = {
            [EventType.USER_REGISTERED]: 'bg-green-100 text-green-800',
            [EventType.USER_LOGIN]: 'bg-blue-100 text-blue-800',
            [EventType.FACULTY_CREATED]: 'bg-purple-100 text-purple-800',
            [EventType.FACULTY_UPDATED]: 'bg-yellow-100 text-yellow-800',
            [EventType.FACULTY_DELETED]: 'bg-red-100 text-red-800',
            [EventType.CAREER_CREATED]: 'bg-indigo-100 text-indigo-800',
            [EventType.CAREER_UPDATED]: 'bg-orange-100 text-orange-800',
            [EventType.CAREER_DELETED]: 'bg-pink-100 text-pink-800',
        };

        const colorClass = colors[eventType] || 'bg-gray-100 text-gray-800';

        return (
            <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${colorClass}`}>
                {eventType}
            </span>
        );
    };

    if (logs.length === 0) {
        return (
            <div className="bg-white rounded-xl p-12 text-center shadow-lg border border-gray-200">
                <FileText size={48} className="mx-auto text-gray-400 mb-4" />
                <p className="text-gray-600 text-lg">No hay logs de auditoría disponibles</p>
            </div>
        );
    }

    return (
        <div className="bg-white rounded-xl shadow-lg border border-gray-200 overflow-hidden">
            <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                    <tr>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            ID
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Tipo de Evento
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Usuario
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Acción
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Fecha
                        </th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Detalles
                        </th>
                    </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                    {logs.map((log) => (
                        <tr key={log.id} className="hover:bg-gray-50 transition-colors">
                            <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                                #{log.id}
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm">
                                {getEventTypeBadge(log.eventType)}
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                <div className="flex items-center gap-2">
                                    <User size={16} className="text-gray-400" />
                                    <div>
                                        <div className="font-medium">{log.userEmail || 'N/A'}</div>
                                        {log.userId && (
                                            <div className="text-xs text-gray-500">ID: {log.userId}</div>
                                        )}
                                    </div>
                                </div>
                            </td>
                            <td className="px-6 py-4 text-sm text-gray-900">
                                <div className="flex items-center gap-2">
                                    <Tag size={16} className="text-gray-400" />
                                    {log.action}
                                </div>
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                <div className="flex items-center gap-2">
                                    <Calendar size={16} className="text-gray-400" />
                                    {formatDate(log.timestamp)}
                                </div>
                            </td>
                            <td className="px-6 py-4 text-sm text-gray-500 max-w-xs truncate">
                                {log.details || '-'}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};