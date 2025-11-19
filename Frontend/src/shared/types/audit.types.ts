export const EventType = {
    USER_REGISTERED: 'USER_REGISTERED',
    USER_LOGIN: 'USER_LOGIN',
    FACULTY_CREATED: 'FACULTY_CREATED',
    FACULTY_UPDATED: 'FACULTY_UPDATED',
    FACULTY_DELETED: 'FACULTY_DELETED',
    CAREER_CREATED: 'CAREER_CREATED',
    CAREER_UPDATED: 'CAREER_UPDATED',
    CAREER_DELETED: 'CAREER_DELETED'
} as const;

export type EventType = typeof EventType[keyof typeof EventType];

export interface AuditLog {
    id: number;
    eventType: EventType;
    userId: number | null;
    userEmail: string | null;
    action: string;
    details: string | null;
    ipAddress: string | null;
    userAgent: string | null;
    timestamp: string;
    status: string | null;
    entityType: string | null;
    entityId: number | null;
}

export interface AuditStatistics {
    totalEvents: number;
    eventsByType: Record<string, number>;
    latestEvent: string | null;
    oldestEvent: string | null;
}