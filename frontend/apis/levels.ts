export interface ClimbingGymLevel {
    id: number;
    startGrade: number;
    endGrade: number;
    color: string;
}

export const fetchClimbingGymLevels = async (gymId: number): Promise<ClimbingGymLevel[]> => {
    try {
        const token = localStorage.getItem("token");
        const response = await fetch(`http://localhost:8080/api/v1/climbing-gyms/${gymId}/levels`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
            },
        });
        if (!response.ok) {
            throw new Error(`Failed to fetch climbing gym levels: ${response.status}`);
        }
        const data: ClimbingGymLevel[] = await response.json();
        return data;
    } catch (error) {
        throw new Error((error as Error).message);
    }
};

type ColorAttributes = {
    key: string;
    displayName: string;
    bgColor: string;
    textColor: string;
    borderColor: string;
};

export enum Color {
    RED,
    BLUE,
    GREEN,
    YELLOW,
    ORANGE,
    PURPLE,
    PINK,
    BLACK,
    WHITE,
    GREY,
    BROWN,
    CYAN,
    MAGENTA,
}

const Colors: { [key: string]: ColorAttributes } = {
    RED: {key: "RED", displayName: "빨강", bgColor: "bg-red-500", textColor: "text-white", borderColor: "border-red-600"},
    BLUE: {
        key: "BLUE",
        displayName: "파랑",
        bgColor: "bg-blue-500",
        textColor: "text-white",
        borderColor: "border-blue-600"
    },
    GREEN: {
        key: "GREEN",
        displayName: "초록",
        bgColor: "bg-green-500",
        textColor: "text-white",
        borderColor: "border-green-600"
    },
    YELLOW: {
        key: "YELLOW",
        displayName: "노랑",
        bgColor: "bg-yellow-400",
        textColor: "text-black",
        borderColor: "border-yellow-500"
    },
    ORANGE: {
        key: "ORANGE",
        displayName: "주황",
        bgColor: "bg-orange-400",
        textColor: "text-black",
        borderColor: "border-orange-500"
    },
    PURPLE: {
        key: "PURPLE",
        displayName: "보라",
        bgColor: "bg-purple-500",
        textColor: "text-white",
        borderColor: "border-purple-600"
    },
    PINK: {
        key: "PINK",
        displayName: "분홍",
        bgColor: "bg-pink-400",
        textColor: "text-black",
        borderColor: "border-pink-500"
    },
    BLACK: {
        key: "BLACK",
        displayName: "검정",
        bgColor: "bg-gray-900",
        textColor: "text-white",
        borderColor: "border-gray-800"
    },
    WHITE: {
        key: "WHITE",
        displayName: "하양",
        bgColor: "bg-gray-100",
        textColor: "text-black",
        borderColor: "border-gray-300"
    },
    GREY: {
        key: "GREY",
        displayName: "회색",
        bgColor: "bg-gray-500",
        textColor: "text-white",
        borderColor: "border-gray-600"
    },
    BROWN: {
        key: "BROWN",
        displayName: "갈색",
        bgColor: "bg-amber-700",
        textColor: "text-white",
        borderColor: "border-amber-800"
    },
    CYAN: {
        key: "CYAN",
        displayName: "청록",
        bgColor: "bg-cyan-400",
        textColor: "text-black",
        borderColor: "border-cyan-500"
    },
    MAGENTA: {
        key: "MAGENTA",
        displayName: "자홍",
        bgColor: "bg-fuchsia-500",
        textColor: "text-white",
        borderColor: "border-fuchsia-600"
    }
};

export default Colors;