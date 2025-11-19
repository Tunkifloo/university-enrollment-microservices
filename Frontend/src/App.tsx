import { useEffect, useState } from "react";
import { Layout } from "./shared/components/Layout";
import { FacultadesPage } from "./features/facultades/FacultadesPage";
import { CarrerasPage } from "./features/carreras/CarrerasPage";
import { AuditPage } from "./features/audit/AuditPage";
import { LoginPage } from "./features/auth/LoginPage";
import { useAuthStore } from "./shared/store/authStore";
import { authService } from "./shared/services/authService";
import { Loading } from "./shared/components/Loading";

export type AppTab = "facultades" | "carreras" | "audit";

function App() {
    const [activeTab, setActiveTab] = useState<AppTab>("facultades");
    const { isAuthenticated, initializeAuth } = useAuthStore();
    const [isInitializing, setIsInitializing] = useState(true);
    const [isAdmin, setIsAdmin] = useState(false);

    useEffect(() => {
        // Inicializar autenticación desde localStorage
        initializeAuth();

        // Verificar si es admin
        const adminStatus = authService.isAdmin();
        setIsAdmin(adminStatus);

        setIsInitializing(false);
    }, [initializeAuth]);

    // Actualizar isAdmin cuando cambia la autenticación
    useEffect(() => {
        if (isAuthenticated) {
            setIsAdmin(authService.isAdmin());
        } else {
            setIsAdmin(false);
        }
    }, [isAuthenticated]);

    // Mostrar loading mientras se inicializa
    if (isInitializing) {
        return (
            <div className="min-h-screen flex items-center justify-center bg-gray-50">
                <Loading />
            </div>
        );
    }

    // Si NO está autenticado, mostrar login
    if (!isAuthenticated) {
        return <LoginPage />;
    }

    // Función para renderizar el contenido según la tab activa
    const renderContent = () => {
        switch (activeTab) {
            case "facultades":
                return <FacultadesPage />;
            case "carreras":
                return <CarrerasPage />;
            case "audit":
                return isAdmin ? <AuditPage /> : <FacultadesPage />;
            default:
                return <FacultadesPage />;
        }
    };

    // Si está autenticado, mostrar la app principal
    return (
        <Layout
            activeTab={activeTab}
            onTabChange={setActiveTab}
            showAuditTab={isAdmin}
        >
            {renderContent()}
        </Layout>
    );
}

export default App;