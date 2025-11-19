import { BarChart3, Activity, Clock, TrendingUp } from 'lucide-react';
import type { AuditStatistics } from '../../../shared/types/audit.types';

interface AuditStatisticsProps {
    statistics: AuditStatistics;
}

export const AuditStatisticsComponent = ({ statistics }: AuditStatisticsProps) => {
    const formatDate = (dateStr: string | null) => {
        if (!dateStr) return 'N/A';
        return new Date(dateStr).toLocaleString('es-PE', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    const eventTypeLabels: Record<string, string> = {
        'USER_REGISTERED': 'Registros de Usuario',
        'USER_LOGIN': 'Inicios de Sesión',
        'FACULTY_CREATED': 'Facultades Creadas',
        'FACULTY_UPDATED': 'Facultades Actualizadas',
        'FACULTY_DELETED': 'Facultades Eliminadas',
        'CAREER_CREATED': 'Carreras Creadas',
        'CAREER_UPDATED': 'Carreras Actualizadas',
        'CAREER_DELETED': 'Carreras Eliminadas',
    };

    return (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            {/* Total Events */}
            <div className="bg-gradient-to-br from-blue-500 to-blue-600 rounded-xl p-6 text-white shadow-lg">
                <div className="flex items-center justify-between mb-4">
                    <Activity size={32} />
                    <span className="text-3xl font-bold">{statistics.totalEvents}</span>
                </div>
                <h3 className="text-sm font-medium opacity-90">Total de Eventos</h3>
            </div>

            {/* Latest Event */}
            <div className="bg-gradient-to-br from-green-500 to-green-600 rounded-xl p-6 text-white shadow-lg">
                <div className="flex items-center justify-between mb-4">
                    <Clock size={32} />
                    <TrendingUp size={24} />
                </div>
                <h3 className="text-sm font-medium opacity-90 mb-1">Último Evento</h3>
                <p className="text-xs">{formatDate(statistics.latestEvent)}</p>
            </div>

            {/* Oldest Event */}
            <div className="bg-gradient-to-br from-purple-500 to-purple-600 rounded-xl p-6 text-white shadow-lg">
                <div className="flex items-center justify-between mb-4">
                    <Clock size={32} />
                    <BarChart3 size={24} />
                </div>
                <h3 className="text-sm font-medium opacity-90 mb-1">Primer Evento</h3>
                <p className="text-xs">{formatDate(statistics.oldestEvent)}</p>
            </div>

            {/* Event Types Count */}
            <div className="bg-gradient-to-br from-orange-500 to-orange-600 rounded-xl p-6 text-white shadow-lg">
                <div className="flex items-center justify-between mb-4">
                    <BarChart3 size={32} />
                    <span className="text-3xl font-bold">
                        {Object.keys(statistics.eventsByType).length}
                    </span>
                </div>
                <h3 className="text-sm font-medium opacity-90">Tipos de Eventos</h3>
            </div>

            {/* Events by Type - Full Width */}
            <div className="col-span-full bg-white rounded-xl p-6 shadow-lg border border-gray-200">
                <h3 className="text-lg font-bold text-gray-800 mb-4 flex items-center gap-2">
                    <BarChart3 size={20} className="text-blue-600" />
                    Distribución de Eventos
                </h3>
                <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                    {Object.entries(statistics.eventsByType).map(([type, count]) => (
                        <div key={type} className="bg-gray-50 rounded-lg p-4 border border-gray-200">
                            <div className="flex justify-between items-center mb-2">
                                <span className="text-2xl font-bold text-blue-600">{count}</span>
                            </div>
                            <p className="text-xs text-gray-600">
                                {eventTypeLabels[type] || type}
                            </p>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};