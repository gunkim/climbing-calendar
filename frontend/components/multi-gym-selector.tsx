// "use client"
//
// import * as React from "react"
// import { MapPin, Search, X, ChevronDown, Plus } from "lucide-react"
// import { Button } from "@/components/ui/button"
// import { Input } from "@/components/ui/input"
// import { useClickOutside } from "@/hooks/use-click-outside"
// import { gymDifficultySystems } from "@/data/gym-difficulty-systems"
//
// interface MultiGymSelectorProps {
//   selectedGyms: string[]
//   onGymToggle: (gymId: string) => void
// }
//
// export function MultiGymSelector({ selectedGyms, onGymToggle }: MultiGymSelectorProps) {
//   const [open, setOpen] = React.useState(false)
//   const [searchQuery, setSearchQuery] = React.useState("")
//   const dropdownRef = React.useRef<HTMLDivElement>(null)
//
//   // 검색 결과 필터링 - 암장 체인 이름으로 검색
//   const filteredGymSystems = gymDifficultySystems
//     .slice(1)
//     .filter(
//       (system) => system.name.toLowerCase().includes(searchQuery.toLowerCase()) && !selectedGyms.includes(system.id),
//     )
//
//   // 암장 선택 핸들러
//   const handleSelectGym = (gymId: string) => {
//     onGymToggle(gymId)
//     setOpen(false)
//     setSearchQuery("")
//   }
//
//   // 검색어 초기화
//   const clearSearch = () => {
//     setSearchQuery("")
//   }
//
//   // 외부 클릭 감지
//   useClickOutside(dropdownRef, () => {
//     if (open) setOpen(false)
//   })
//
//   // 포커스 처리
//   React.useEffect(() => {
//     if (open) {
//       const searchInput = document.getElementById("gym-search-input")
//       if (searchInput) {
//         searchInput.focus()
//       }
//     }
//   }, [open])
//
//   // 선택된 암장 시스템 가져오기
//   const getGymSystemById = (id: string) => {
//     return gymDifficultySystems.find((sys) => sys.id === id) || gymDifficultySystems[0]
//   }
//
//   return (
//     <div className="w-full">
//       <div className="flex flex-wrap gap-2 mb-4">
//         {selectedGyms.map((gymId) => {
//           const system = getGymSystemById(gymId)
//           return (
//             <div key={gymId} className="flex items-center bg-blue-50 text-blue-700 px-3 py-1.5 rounded-full">
//               <span>{system.name}</span>
//               <Button
//                 variant="ghost"
//                 size="icon"
//                 className="h-5 w-5 ml-1 text-blue-700 hover:bg-blue-100 rounded-full"
//                 onClick={() => onGymToggle(gymId)}
//               >
//                 <X className="h-3 w-3" />
//               </Button>
//             </div>
//           )
//         })}
//       </div>
//
//       <div className="relative w-full" ref={dropdownRef}>
//         <Button
//           type="button"
//           variant="outline"
//           role="combobox"
//           aria-expanded={open}
//           className="w-full justify-between border-2 border-blue-200 hover:bg-blue-50 text-blue-700"
//           onClick={() => setOpen(!open)}
//         >
//           <div className="flex items-center">
//             <Plus className="mr-2 h-4 w-4 text-blue-500" />
//             <span>암장 추가하기</span>
//           </div>
//           <ChevronDown
//             className={`ml-2 h-4 w-4 shrink-0 transition-transform duration-200 text-blue-500 ${open ? "rotate-180" : ""}`}
//           />
//         </Button>
//
//         {open && (
//           <div className="absolute z-50 mt-1 w-full rounded-md border border-blue-200 bg-white shadow-md animate-in fade-in-0 zoom-in-95">
//             <div className="p-3 space-y-3">
//               <div className="flex items-center gap-2">
//                 <div className="relative flex-1">
//                   <Search className="absolute left-2 top-2.5 h-4 w-4 text-blue-400" />
//                   <Input
//                     id="gym-search-input"
//                     placeholder="암장 검색..."
//                     value={searchQuery}
//                     onChange={(e) => setSearchQuery(e.target.value)}
//                     className="pl-8 border-blue-200"
//                   />
//                   {searchQuery && (
//                     <Button
//                       variant="ghost"
//                       size="sm"
//                       className="absolute right-1 top-1.5 h-6 w-6 p-0 hover:bg-blue-50 text-blue-500"
//                       onClick={clearSearch}
//                     >
//                       <X className="h-4 w-4" />
//                     </Button>
//                   )}
//                 </div>
//               </div>
//
//               <div className="max-h-[200px] overflow-y-auto pr-1 rounded-md">
//                 <div className="space-y-1">
//                   {filteredGymSystems.length === 0 ? (
//                     <div className="py-6 text-center text-sm text-blue-400">검색 결과가 없습니다.</div>
//                   ) : (
//                     filteredGymSystems.map((system) => {
//                       // 해당 체인의 암장 목록 가져오기
//                       const chainGyms = climbingGyms.filter((gym) => gym.name.includes(system.name)).slice(0, 3) // 최대 3개만 표시
//
//                       return (
//                         <Button
//                           key={system.id}
//                           variant="ghost"
//                           className="w-full justify-start text-left hover:bg-blue-50 hover:text-blue-700 text-blue-600"
//                           onClick={() => handleSelectGym(system.id)}
//                         >
//                           <div className="flex flex-col items-start">
//                             <span className="font-medium">{system.name}</span>
//                             {chainGyms.length > 0 && (
//                               <span className="text-xs text-blue-400 flex items-center">
//                                 <MapPin className="mr-1 h-3 w-3" />
//                                 {chainGyms.map((gym) => gym.name).join(", ")}
//                                 {climbingGyms.filter((gym) => gym.name.includes(system.name)).length > 3 && " 외"}
//                               </span>
//                             )}
//                           </div>
//                         </Button>
//                       )
//                     })
//                   )}
//                 </div>
//               </div>
//             </div>
//           </div>
//         )}
//       </div>
//     </div>
//   )
// }
//
