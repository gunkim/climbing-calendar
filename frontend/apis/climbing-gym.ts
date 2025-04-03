export interface GetClimbingGymResponse {
    id: number;
    name: string;
    address: string;
    latitude: number;
    longitude: number;
    isParkingAvailable: boolean;
}

export async function fetchClimbingGyms(): Promise<GetClimbingGymResponse[]> {
    try {
        const token = localStorage.getItem("token");
        const response = await fetch("http://localhost:8080/api/v1/climbing-gyms", {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });
        if (!response.ok) {
            throw new Error(`Failed to fetch climbing gyms: ${response.statusText}`);
        }
        const data: GetClimbingGymResponse[] = await response.json();
        return data;
    } catch (error) {
        console.error("Error occurred while fetching climbing gyms", error);
        throw error;
    }
}

export interface CreateScheduleRequest {
    title: string;
    scheduleDate: string;
    climbingGymId: number;
    memo: string;
    clearList: ClearItem[];
}

export interface ClearItem {
    levelId: number;
    count: number;
}

export async function createSchedule(request: CreateScheduleRequest): Promise<void> {
    try {
        const token = localStorage.getItem("token");
        const response = await fetch("http://localhost:8080/api/v1/schedules", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`,
            },
            body: JSON.stringify(request),
        });

        if (!response.ok) {
            throw new Error(`Failed to create schedule: ${response.statusText}`);
        }

        console.log("Schedule successfully created!");
    } catch (error) {
        console.error("Error occurred while creating schedule", error);
        throw error;
    }
}

export interface ExtendedClearItem {
    id: number;
    color: string;
    startGrade: number;
    endGrade: number;
    count: number;
}

export interface GetScheduleResponse {
    id: number;
    title: string;
    scheduleDate: string;
    memo: string;
    climbingGymId: number;
    climbingGymName: string;
    clearList: ExtendedClearItem[];
}

export async function fetchSchedules(): Promise<GetScheduleResponse[]> {
    try {
        const token = localStorage.getItem("token");
        const response = await fetch("http://localhost:8080/api/v1/schedules", {
            headers: {
                "Authorization": `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error(`Failed to fetch schedules: ${response.statusText}`);
        }

        const data: GetScheduleResponse[] = await response.json();
        return data;
    } catch (error) {
        console.error("Error occurred while fetching schedules", error);
        throw error;
    }
}


export async function updateSchedule(id: number, request: CreateScheduleRequest): Promise<void> {
    try {
        const token = localStorage.getItem("token");
        const response = await fetch(`http://localhost:8080/api/v1/schedules/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`,
            },
            body: JSON.stringify(request),
        });

        if (!response.ok) {
            throw new Error(`Failed to update schedule: ${response.statusText}`);
        }

        console.log("Schedule successfully updated!");
    } catch (error) {
        console.error("Error occurred while updating schedule", error);
        throw error;
    }
}

export async function deleteSchedule(id: number): Promise<void> {
    try {
        const token = localStorage.getItem("token");
        const response = await fetch(`http://localhost:8080/api/v1/schedules/${id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error(`Failed to update schedule: ${response.statusText}`);
        }

        console.log("Schedule successfully updated!");
    } catch (error) {
        console.error("Error occurred while updating schedule", error);
        throw error;
    }
}