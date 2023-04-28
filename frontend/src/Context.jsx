import {createContext, useContext, useMemo, useState} from "react";

export const GlobalContext = createContext({})
export const useGlobalContext = () => useContext(GlobalContext)

const AppContext = ({children}) => {

    const [isModalOpen, setIsModalOpen] = useState(true)

    const openModal = () => {
        setIsModalOpen(true)
    }
    const closeModal = () => {
        setIsModalOpen(false)
    }

    const value = useMemo(() => {
        return {isModalOpen, openModal,closeModal}
    }, [isModalOpen])

    return (
        <GlobalContext.Provider value={value}>
            {children}
        </GlobalContext.Provider>
    );

}
export default AppContext