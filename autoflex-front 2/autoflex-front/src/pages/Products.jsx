import { useEffect, useState } from "react";
import { productService, rawMaterialService } from "../services/api";

export default function Products() {
  const [products, setProducts] = useState([]);
  const [materials, setMaterials] = useState([]);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState("");

  const [form, setForm] = useState({
    code: "",
    name: "",
    price: ""
  });

  const [selectedMaterial, setSelectedMaterial] = useState({});
  const [quantityRequired, setQuantityRequired] = useState({});

  async function loadProducts() {
    const data = await productService.getAll();
    setProducts(data);
  }

  async function loadMaterials() {
    const data = await rawMaterialService.getAll();
    setMaterials(data);
  }

  function showMessage(text) {
    setMessage(text);
    setTimeout(() => setMessage(""), 2500);
  }

  async function handleDelete(id) {
    await productService.delete(id);
    showMessage("Product deleted");
    loadProducts();
  }

  useEffect(() => {
    async function fetchData() {
      await loadProducts();
      await loadMaterials();
      setLoading(false);
    }
    fetchData();
  }, []);

  async function handleCreate(e) {
    e.preventDefault();

    await productService.create({
      ...form,
      price: Number(form.price)
    });

    setForm({ code: "", name: "", price: "" });
    showMessage("Product created");
    loadProducts();
  }

  async function addMaterial(productId) {
    if (!selectedMaterial[productId] || !quantityRequired[productId]) return;

    await fetch("http://localhost:8080/product-materials?" +
      new URLSearchParams({
        productId,
        rawMaterialId: selectedMaterial[productId],
        quantityRequired: quantityRequired[productId]
      }), { method: "POST" });

    showMessage("Material added to product");
    loadProducts();
  }

  return (
    <div className="max-w-5xl mx-auto p-8">

      <h1 className="text-3xl font-bold mb-6">Products</h1>

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
          placeholder="Code"
          className="border p-2 rounded w-32"
          value={form.code}
          onChange={(e) => setForm({ ...form, code: e.target.value })}
        />

        <input
          placeholder="Name"
          className="border p-2 rounded flex-1"
          value={form.name}
          onChange={(e) => setForm({ ...form, name: e.target.value })}
        />

        <input
          type="number"
          placeholder="Price"
          className="border p-2 rounded w-32"
          value={form.price}
          onChange={(e) => setForm({ ...form, price: e.target.value })}
        />

        <button
          disabled={!form.code || !form.name || !form.price}
          className="bg-blue-600 text-white px-4 rounded disabled:bg-gray-300"
        >
          Create
        </button>
      </form>

      {loading && <p className="text-gray-500">Loading products...</p>}

      {!loading && products.length === 0 && (
        <p className="text-gray-400">No products created yet.</p>
      )}

      <div className="flex flex-col gap-6">

        {products.map(product => (
          <div key={product.id} className="border rounded shadow-sm p-5 bg-white">

            <div className="flex justify-between items-center">
              <h2 className="text-xl font-bold">{product.name}</h2>
              <span className="font-semibold text-green-600">
                ${product.price}
              </span>
            </div>

            <p className="text-sm text-gray-500">
              Code: {product.code}
            </p>

            <div className="mt-4">
              <p className="font-semibold mb-1">Materials</p>

              {product.materials.length === 0 ? (
                <p className="text-sm text-gray-400">
                  No materials associated
                </p>
              ) : (
                product.materials.map(m => (
                  <div key={m.id} className="text-sm">
                    {m.rawMaterial.name} – Qty: {m.quantityRequired}
                  </div>
                ))
              )}
            </div>

            <div className="flex gap-2 mt-4 flex-wrap">
              <select
                className="border p-2 rounded"
                onChange={(e) =>
                  setSelectedMaterial({
                    ...selectedMaterial,
                    [product.id]: e.target.value
                  })
                }
              >
                <option value="">Select material</option>
                {materials.map(m => (
                  <option key={m.id} value={m.id}>
                    {m.name}
                  </option>
                ))}
              </select>

              <input
                type="number"
                placeholder="Qty"
                className="border p-2 rounded w-24"
                onChange={(e) =>
                  setQuantityRequired({
                    ...quantityRequired,
                    [product.id]: e.target.value
                  })
                }
              />

              <button
                onClick={() => addMaterial(product.id)}
                className="bg-green-600 text-white px-3 rounded"
              >
                Add
              </button>

              <button
                onClick={() => handleDelete(product.id)}
                className="bg-red-500 text-white px-3 rounded"
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
