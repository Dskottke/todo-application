import {useGlobalContext} from "./Context.jsx";
import {useMutation, useQuery, useQueryClient} from "react-query";
import customToDoFetch from "./customFetches.js";
import {toast} from "react-toastify";

export const useSortData = (toDos) => {

    const openList = toDos.filter(todo => todo.status === "OPEN")
    const inProgressList = toDos.filter(todo => todo.status === "IN_PROGRESS")
    const closed = toDos.filter(todo => todo.status === "DONE")

    return {openList, inProgressList, closed}
}
export const useFetchToDos = () => {
    const {setToDos} = useGlobalContext()

    const {isSuccess, isLoading, data, error} = useQuery({
        queryKey: ['todos'], queryFn: () => customToDoFetch.get(""), onSuccess: (data) => {
            setToDos(data.data)
        }
    })
    return {isSuccess, isLoading, data, error}
}

export const useAddToDo = () => {
    const queryClient = useQueryClient()
    const {mutate: createTask, isLoading} = useMutation({
        mutationFn: (newTodo) => {
            return customToDoFetch.post('', newTodo)
        }, onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['todos']})
            toast.success("New todo added!")
        }, onError: (error) => {
            toast.error(error.message)
        }

    })
    return {createTask, isLoading}
}

export const useAdvanceToDo = () => {
    const queryClient = useQueryClient()

    const {mutate: advanceToDo} = useMutation({
        mutationFn: (id) => customToDoFetch.put(`/${id}`), onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['todos']})
        }
    })
    return {mutate: advanceToDo}
}

export const useDeleteToDo = () => {
    const queryClient = useQueryClient()
    const {mutate: deleteToDo} = useMutation({
        mutationFn: (id) => customToDoFetch.delete(`/${id}`), onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['todos']})
        }
    })
    return {mutate: deleteToDo}
}
export const useDateFormatter = (date) => {
    const dateFormatted = new Date(date).toLocaleDateString("de-DE", {
        year: "numeric",
        month: "long",
        day: "numeric"
    })
    return dateFormatted
}