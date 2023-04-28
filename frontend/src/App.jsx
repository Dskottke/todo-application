
import Navbar from "./Navbar.jsx";
import Hero from "./Hero.jsx";
import BoardOverview from "./BoardOverview.jsx";
import AddModal from "./AddModal.jsx";
import {useGlobalContext} from "./Context.jsx";

function App() {

    const {isModalOpen} = useGlobalContext()
    console.log(isModalOpen)

    return (
        <div className="App">
            <Navbar/>
            {isModalOpen && <AddModal/> }
            <Hero/>
            <BoardOverview/>
        </div>
    )
}

export default App
