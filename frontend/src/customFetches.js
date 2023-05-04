import axios from "axios";

const customToDoFetch = axios.create({
    baseURL: "/api/todo"
});

export default customToDoFetch;


