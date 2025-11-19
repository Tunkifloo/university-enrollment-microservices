import { useEffect } from 'react';
import { Shield, RefreshCw } from 'lucide-react';
import { useAuditStore } from './store/auditStore';
import { AuditStatisticsComponent } from './components/AuditStatistics';
import { AuditLogsTable } from './components/AuditLogsTable';
import { Loading } from '../../shared/components/Loading';

export const AuditPage = () => {
    const { logs, statistics, isLoading, error, fetchLogs, fetchStatistics, clearError } = useAuditStore();

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        await Promise.all([
            fetchLogs(),
            fetchStatistics()
        ]);
    };

    const handleRefresh = () => {
        clearError();
        loadData();
    };

    if (isLoading && logs.length === 0) {
        return (
            <div className="flex items-center justify-center h-96">
                <Loading />
            </div>
        );
    }

    return (
        <div className="p-6 space-y-6">
            {/* Header */}
            <div className="flex items-center justify-between">
                <div className="flex items-center gap-3">
                    <div className="bg-blue-600 p-3 rounded-lg">
                        <Shield size={28} className="text-white" />
                    </div>
                    <div>
                        <h1 className="text-2xl font-bold text-gray-800">Panel de Auditoría</h1>
                        <p className="text-gray-600">Monitoreo y registro de eventos del sistema</p>
                    </div>
                </div>

                {/* Botón sin variant - usando solo clases de Tailwind */}
                <button
                    onClick={handleRefresh}
                    disabled={isLoading}
                    className="flex items-center gap-2 px-4 py-2 bg-white border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                >
                    <RefreshCw size={18} className={isLoading ? 'animate-spin' : ''} />
                    Actualizar
                </button>
            </div>

            {/* Error Message */}
            {error && (
                <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
                    <p className="font-medium">Error</p>
                    <p className="text-sm">{error}</p>
                </div>
            )}

            {/* Statistics */}
            {statistics && <AuditStatisticsComponent statistics={statistics} />}

            {/* Logs Table */}
            <div>
                <h2 className="text-xl font-bold text-gray-800 mb-4">Registro de Eventos</h2>
                <AuditLogsTable logs={logs} />
            </div>
        </div>
    );
};