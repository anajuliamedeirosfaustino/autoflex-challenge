import { Routes, Route, Link } from "react-router-dom";
import Products from "./pages/Products";
import RawMaterials from "./pages/RawMaterials";
import Production from "./pages/Production";


export default function App() {
  return (
    <div>
      <nav className="p-4 bg-gray-200 flex gap-4">
        <Link to="/">Products</Link>
        <Link to="/raw-materials">Raw Materials</Link>
        <Link to="/production">Production</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Products />} />
        <Route path="/raw-materials" element={<RawMaterials />} />
        <Route path="/production" element={<Production />} />
      </Routes>
    </div>
  );
}
