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
