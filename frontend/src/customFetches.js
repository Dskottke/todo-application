import axios from "axios";

const customToDoFetch = axios.create({
    baseURL: "/api/todo"
});
const customUserFetch = axios.create({
    baseURL: "/api/user"
});

export default {customToDoFetch, customUserFetch};


