import { useState } from "react";
import { productionService } from "../services/api";

export default function Production() {
  const [result, setResult] = useState([]);
  const [loading, setLoading] = useState(false);

  async function handleCalculate() {
    setLoading(true);
    const data = await productionService.calculate();
    setResult(data);
    setLoading(false);
  }

  const totalValue = result.reduce(
    (acc, item) => acc + Number(item.totalValue),
    0
  );

  return (
    <div className="max-w-4xl mx-auto p-8">

      <h1 className="text-3xl font-bold mb-6">Production</h1>

      <button
        onClick={handleCalculate}
        disabled={loading}
        className="bg-green-600 text-white px-5 py-2 rounded disabled:bg-gray-300 mb-6"
      >
        {loading ? "Calculating..." : "Calculate Production"}
      </button>

      {!loading && result.length === 0 && (
        <p className="text-gray-400">
          Click calculate to simulate production.
        </p>
      )}

      {!loading && result.length > 0 && (
        <div className="mb-6 p-4 bg-green-50 rounded">
          <p className="font-semibold text-green-700">
            Total Production Value: ${totalValue.toFixed(2)}
          </p>
        </div>
      )}

      <div className="flex flex-col gap-4">
        {result.map((r, index) => (
          <div key={index} className="border rounded p-4 bg-white shadow-sm">
            <p className="font-semibold">{r.productName}</p>
            <p className="text-sm text-gray-600">
              Quantity: {r.quantity}
            </p>
            <p className="text-sm text-gray-600">
              Total Value: ${Number(r.totalValue).toFixed(2)}
            </p>
          </div>
        ))}
      </div>

    </div>
  );
}
