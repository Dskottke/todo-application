import {useGlobalContext} from "./Context.jsx";
import {useMutation, useQuery, useQueryClient} from "react-query";
import fetch from "./customFetches.js";
import {toast} from "react-toastify";
import {useEffect, useMemo, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";

export const useSortData = (toDos) => {
    const openList = toDos.filter(todo => todo.status === "OPEN")
    const inProgressList = toDos.filter(todo => todo.status === "IN_PROGRESS")
    const closed = toDos.filter(todo => todo.status === "DONE")
    return {openList, inProgressList, closed}
}

export const useFetchToDos = () => {
    const {setToDos} = useGlobalContext()
    const {isSuccess, isLoading, data, error} = useQuery({
        queryKey: ['todos'], queryFn: () => fetch.customToDoFetch.get(""), onSuccess: (data) => {
            setToDos(data.data)
        }
    })
    return {isSuccess, isLoading, data, error}
}
export const useUnconfirmedUsersFetch = () => {
    const {data, isLoading, isError, isSuccess} = useQuery({
        queryKey: ['unconfirmedUsers'], queryFn: () => fetch.customUserFetch.get("confirm/list"),
        onSuccess: (data) => {
            console.log(data.data)
        },
        onError: (error) => {
            console.log(error)
        }
    })
    return {data, isLoading, isError, isSuccess}
}

export const useDeleteUser = () => {
    const queryClient = useQueryClient()
    const {mutate: deleteUser} = useMutation({
        mutationFn: (id) => fetch.customUserFetch.delete(`/${id}`), onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['unconfirmedUsers']})
        }
    })
    return {mutate: deleteUser}
}
export const useConfirmUser = () => {
    const queryClient = useQueryClient()
    const {mutate: confirmUser} = useMutation({
        mutationFn: (id) => fetch.customUserFetch.put(`/confirm/${id}`), onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['unconfirmedUsers']})
        }
    })
    return {mutate: confirmUser}
}

export const useAddToDo = () => {
    const queryClient = useQueryClient()
    const {mutate: createTask, isLoading} = useMutation({
        mutationFn: (newTodo) => {
            return fetch.customToDoFetch.post('', newTodo)
        }, onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['todos']})
            toast.success("New todo added!")
        }, onError: (error) => {
            toast.error(error.response.data)
        }
    })
    return {mutate: createTask, isLoading}
}

export const useAdvanceToDo = () => {
    const queryClient = useQueryClient()
    const {mutate: advanceToDo} = useMutation({
        mutationFn: (id) => fetch.customToDoFetch.put(`/${id}`), onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['todos']})
        }
    })
    return {mutate: advanceToDo}
}

export const useDeleteToDo = () => {
    const queryClient = useQueryClient()
    const {mutate: deleteToDo} = useMutation({
        mutationFn: (id) => fetch.customToDoFetch.delete(`/${id}`), onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['todos']})
        }
    })
    return {mutate: deleteToDo}
}

export const useDateFormatter = (creationDate, dueDate) => {
    const creationDateFormatted = useMemo(() => {
        return new Date(creationDate).toLocaleDateString("de-DE", {
            year: "numeric", month: "long", day: "numeric"
        })
    }, [creationDate])

    const dueDateFormatted = useMemo(() => {
        return new Date(dueDate).toLocaleDateString("de-DE", {
            year: "numeric", month: "long", day: "numeric"
        })
    }, [dueDate])

    const daysLeft = useMemo(() => {
        return Math.floor((new Date(dueDate) - new Date()) / (1000 * 60 * 60 * 24) + 1);
    }, [dueDate, creationDate]);

    return {creationDateFormatted, dueDateFormatted, daysLeft}
}

export const useGetColor = (daysLeft) => {
    const getColor = (daysLeft) => {
        if (daysLeft >= 14) {
            return "#48b748"
        }
        if (daysLeft >= 7) {
            return "#ec9900"
        } else {
            return "#d93a3a"
        }
    }
    return getColor(daysLeft)
}

export const useAuth = () => {
    const {setCurrentUser} = useGlobalContext();
    const [user, setUser] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("/api/user/me").then(res => {
            if (res.data.username === "anonymousUser") {
                navigate("/login");
            }
            setCurrentUser(res.data);
            setUser(res.data);
        }).catch(e => {
            if (e.response.status === 401) {
                navigate("/login");
            }
        });
    }, [navigate]);

    return user;

}