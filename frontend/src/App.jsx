
import Navbar from "./Navbar.jsx";
import Hero from "./Hero.jsx";
import BoardOverview from "./BoardOverview.jsx";
import AddModal from "./AddModal.jsx";
import {useGlobalContext} from "./Context.jsx";
import {ToastContainer} from "react-toastify";

function App() {

    const {isModalOpen} = useGlobalContext()
    console.log(isModalOpen)

    return (
        <div className="App">
            <Navbar/>
            {isModalOpen && <AddModal/> }
            <Hero/>
            <BoardOverview/>
            <ToastContainer/>
        </div>
    )
}

export default App
