"use client"

import * as React from "react"
import { MapPin, Search, X, ChevronDown } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { climbingGyms } from "@/data/climbing-gyms"
import { useClickOutside } from "@/hooks/use-click-outside"

interface GymComboboxProps {
  value: string
  onChange: (value: string) => void
  gymColor?: string
}

export function GymCombobox({ value, onChange, gymColor = "sky" }: GymComboboxProps) {
  const [open, setOpen] = React.useState(false)
  const [searchQuery, setSearchQuery] = React.useState("")
  const dropdownRef = React.useRef<HTMLDivElement>(null)

  // 선택된 암장 정보 가져오기
  const selectedGym = climbingGyms.find((gym) => gym.id === value || gym.name === value)

  // 검색 결과 필터링
  const filteredGyms = climbingGyms.filter(
    (gym) =>
      gym.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      gym.location.toLowerCase().includes(searchQuery.toLowerCase()),
  )

  // 암장 선택 핸들러
  const handleSelectGym = (gymName: string) => {
    onChange(gymName)
    setOpen(false)
    setSearchQuery("")
  }

  // 검색어 초기화
  const clearSearch = () => {
    setSearchQuery("")
  }

  // 외부 클릭 감지
  useClickOutside(dropdownRef, () => {
    if (open) setOpen(false)
  })

  // 포커스 처리
  React.useEffect(() => {
    if (open) {
      const searchInput = document.getElementById("gym-search-input")
      if (searchInput) {
        searchInput.focus()
      }
    }
  }, [open])

  return (
    <div className="relative w-full" ref={dropdownRef}>
      <Button
        type="button"
        variant="outline"
        role="combobox"
        aria-expanded={open}
        className={`w-full justify-between border-2 border-${gymColor}-200 hover:bg-${gymColor}-50 text-gray-700`}
        onClick={() => setOpen(!open)}
      >
        {selectedGym ? (
          <div className="flex items-center">
            <MapPin className={`mr-2 h-4 w-4 text-${gymColor}-500`} />
            <span>{selectedGym.name}</span>
          </div>
        ) : (
          <span className="text-gray-500">암장 선택</span>
        )}
        <ChevronDown
          className={`ml-2 h-4 w-4 shrink-0 transition-transform duration-200 text-${gymColor}-500 ${open ? "rotate-180" : ""}`}
        />
      </Button>

      {open && (
        <div
          className={`absolute z-50 mt-1 w-full rounded-md border border-${gymColor}-200 bg-white shadow-md animate-in fade-in-0 zoom-in-95`}
        >
          <div className="p-3 space-y-3">
            <div className="flex items-center gap-2">
              <div className="relative flex-1">
                <Search className={`absolute left-2 top-2.5 h-4 w-4 text-${gymColor}-400`} />
                <Input
                  id="gym-search-input"
                  placeholder="암장 검색..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  className={`pl-8 border-${gymColor}-200`}
                />
                {searchQuery && (
                  <Button
                    variant="ghost"
                    size="sm"
                    className={`absolute right-1 top-1.5 h-6 w-6 p-0 hover:bg-${gymColor}-50 text-${gymColor}-500`}
                    onClick={clearSearch}
                  >
                    <X className="h-4 w-4" />
                  </Button>
                )}
              </div>
            </div>

            <div className="max-h-[200px] overflow-y-auto pr-1 rounded-md">
              <div className="space-y-1">
                {filteredGyms.length === 0 ? (
                  <div className={`py-6 text-center text-sm text-${gymColor}-400`}>검색 결과가 없습니다.</div>
                ) : (
                  filteredGyms.map((gym) => (
                    <Button
                      key={gym.id}
                      variant="ghost"
                      className={`w-full justify-start text-left hover:bg-${gymColor}-50 hover:text-${gymColor}-700 ${
                        selectedGym?.name === gym.name
                          ? `bg-${gymColor}-100 text-${gymColor}-700`
                          : `text-${gymColor}-600`
                      }`}
                      onClick={() => handleSelectGym(gym.name)}
                    >
                      <div className="flex flex-col items-start">
                        <span className="font-medium">{gym.name}</span>
                        <span className={`text-xs text-${gymColor}-400 flex items-center`}>
                          <MapPin className="mr-1 h-3 w-3" />
                          {gym.location} - {gym.address}
                        </span>
                      </div>
                    </Button>
                  ))
                )}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

