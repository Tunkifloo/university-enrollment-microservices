import type {ReactNode} from "react";
import { GraduationCap, Building2, BookOpen, Shield, LogOut } from "lucide-react";
import { useAuthStore } from "../store/authStore";
import type { AppTab } from "../../App";

interface LayoutProps {
    children: ReactNode;
    activeTab: AppTab;
    onTabChange: (tab: AppTab) => void;
    showAuditTab?: boolean;
}

export const Layout = ({ children, activeTab, onTabChange, showAuditTab = false }: LayoutProps) => {
    const { user, logout } = useAuthStore();

    const tabs: Array<{ id: AppTab; label: string; icon: typeof Building2 }> = [
        { id: "facultades", label: "Facultades", icon: Building2 },
        { id: "carreras", label: "Carreras", icon: BookOpen },
    ];

    // Agregar tab de auditoría solo si el usuario es admin
    if (showAuditTab) {
        tabs.push({ id: "audit", label: "Auditoría", icon: Shield });
    }

    const handleLogout = () => {
        if (window.confirm("¿Estás seguro de que deseas cerrar sesión?")) {
            logout();
        }
    };

    return (
        <div className="min-h-screen bg-gray-50">
            {/* Header */}
            <header className="bg-gradient-to-r from-blue-600 to-blue-800 text-white shadow-lg">
                <div className="container mx-auto px-4 py-4">
                    <div className="flex items-center justify-between">
                        <div className="flex items-center gap-3">
                            <GraduationCap size={32} />
                            <div>
                                <h1 className="text-xl font-bold">Sistema de Matrículas</h1>
                                <p className="text-sm text-blue-100">Universidad Nacional</p>
                            </div>
                        </div>

                        {user && (
                            <div className="flex items-center gap-4">
                                <div className="text-right">
                                    <p className="text-sm font-medium">{user.fullName}</p>
                                    <p className="text-xs text-blue-100">{user.email}</p>
                                    {showAuditTab && (
                                        <p className="text-xs text-yellow-300 font-semibold">
                                            {user.role === 'ROLE_ADMIN' ? '👑 Administrador' : user.role}
                                        </p>
                                    )}
                                </div>
                                <button
                                    onClick={handleLogout}
                                    className="bg-white/10 hover:bg-white/20 p-2 rounded-lg transition-colors"
                                    title="Cerrar Sesión"
                                >
                                    <LogOut size={20} />
                                </button>
                            </div>
                        )}
                    </div>
                </div>
            </header>

            {/* Navigation Tabs */}
            <nav className="bg-white shadow-md border-b border-gray-200">
                <div className="container mx-auto px-4">
                    <div className="flex space-x-1">
                        {tabs.map((tab) => {
                            const Icon = tab.icon;
                            const isActive = activeTab === tab.id;

                            return (
                                <button
                                    key={tab.id}
                                    onClick={() => onTabChange(tab.id)}
                                    className={`
                                        flex items-center gap-2 px-6 py-4 font-medium transition-all
                                        ${isActive
                                        ? "text-blue-600 border-b-2 border-blue-600 bg-blue-50"
                                        : "text-gray-600 hover:text-blue-600 hover:bg-gray-50"
                                    }
                                    `}
                                >
                                    <Icon size={20} />
                                    <span>{tab.label}</span>
                                </button>
                            );
                        })}
                    </div>
                </div>
            </nav>

            {/* Main Content */}
            <main className="container mx-auto px-4 py-6">
                {children}
            </main>
        </div>
    );
};