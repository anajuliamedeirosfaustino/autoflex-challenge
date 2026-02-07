import { useEffect, useState } from "react";
import { rawMaterialService } from "../services/api";

export default function RawMaterials() {

  const [materials, setMaterials] = useState([]);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState("");

  const [code, setCode] = useState("");
  const [name, setName] = useState("");
  const [stockQuantity, setStockQuantity] = useState("");

  async function loadMaterials() {
    const data = await rawMaterialService.getAll();
    setMaterials(data);
    setLoading(false);
  }

  function showMessage(text) {
    setMessage(text);
    setTimeout(() => setMessage(""), 2500);
  }

  useEffect(() => {
    loadMaterials();
  }, []);

  async function handleCreate(e) {
    e.preventDefault();

    await rawMaterialService.create({
      code,
      name,
      stockQuantity: Number(stockQuantity),
    });

    setCode("");
    setName("");
    setStockQuantity("");

    showMessage("Material created");
    loadMaterials();
  }

  async function handleDelete(id) {
    await rawMaterialService.delete(id);
    showMessage("Material deleted");
    loadMaterials();
  }

  return (
    <div className="max-w-4xl mx-auto p-8">

      <h1 className="text-3xl font-bold mb-6">Raw Materials</h1>

      {message && (
        <div className="bg-green-100 text-green-700 p-3 rounded mb-6">
          {message}
        </div>
      )}

      <form
        onSubmit={handleCreate}
        className="bg-white shadow rounded p-4 flex flex-wrap gap-3 mb-8"
      >
        <input
          className="border p-2 rounded w-32"
          placeholder="Code"
          value={code}
          onChange={(e) => setCode(e.target.value)}
        />

        <input
          className="border p-2 rounded flex-1"
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <input
          type="number"
          className="border p-2 rounded w-40"
          placeholder="Stock Quantity"
          value={stockQuantity}
          onChange={(e) => setStockQuantity(e.target.value)}
        />

        <button
          disabled={!code || !name || !stockQuantity}
          className="bg-blue-600 text-white px-4 rounded disabled:bg-gray-300"
        >
          Add
        </button>
      </form>

      {loading && (
        <p className="text-gray-500">Loading materials...</p>
      )}

      {!loading && materials.length === 0 && (
        <p className="text-gray-400">
          No raw materials created yet.
        </p>
      )}

      <div className="flex flex-col gap-4">

        {materials.map((m) => (
          <div
            key={m.id}
            className="border rounded shadow-sm p-4 bg-white flex justify-between items-center"
          >
            <div>
              <p className="font-semibold">{m.name}</p>
              <p className="text-sm text-gray-500">
                Code: {m.code}
              </p>
            </div>

            <div className="flex items-center gap-4">
              <span className="font-semibold">
                Stock: {m.stockQuantity}
              </span>

              <button
                onClick={() => handleDelete(m.id)}
                className="bg-red-500 text-white px-3 py-1 rounded"
              >
                Delete
              </button>
            </div>
          </div>
        ))}

      </div>

    </div>
  );
}
