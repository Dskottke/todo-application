import {createContext, useContext, useMemo, useState} from "react";

export const GlobalContext = createContext({})
export const useGlobalContext = () => useContext(GlobalContext)

const AppContext = ({children}) => {

    const [isModalOpen, setIsModalOpen] = useState(false)
    const [toDos, setToDos] = useState({})

    const openAmount = Object.values(toDos).filter((toDo) => toDo.status === "OPEN").length
    const inProgressAmount = Object.values(toDos).filter((toDo) => toDo.status === "IN_PROGRESS").length
    const doneAmount = Object.values(toDos).filter((toDo) => toDo.status === "DONE").length
    const openModal = () => {
        setIsModalOpen(true)
    }
    const closeModal = () => {
        setIsModalOpen(false)
    }

    const value = useMemo(() => {
        return {isModalOpen, openModal, closeModal, toDos,setToDos, openAmount, inProgressAmount, doneAmount}
    }, [isModalOpen, toDos])

    return (
        <GlobalContext.Provider value={value}>
            {children}
        </GlobalContext.Provider>
    );

}
export default AppContext