import { useEffect, useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";
import "../styles/Fines.css";

function Fines() {
  const [fines, setFines] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

const navigate = useNavigate();

  useEffect(() => {
    getFines();
  }, []);

  const getFines = async () => {
    try {
      const response = await api.get("/fines");
      setFines(response.data);
    } catch (error) {
      console.log(error);
      setError("Failed to load fines.");
    } finally {
      setLoading(false);
    }
  };

  const payFine = async (id) => {
  const confirmPay = window.confirm(
    "Are you sure you want to mark this fine as paid?"
  );

  if (!confirmPay) {
    return;
  }

  try {
    await api.put(`/fines/${id}/pay`);

    setFines(
      fines.map((fine) =>
        fine.id === id
          ? { ...fine, paid: true }
          : fine
      )
    );
  } catch (error) {
    console.log(error);
    setError("Failed to pay fine.");
  }
};

  if (loading) {
    return <h2>Loading fines...</h2>;
  }

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div className="fines-page">
      <div className="fines-header">
  <h1>Fines</h1>
</div>

<div className="fine-actions">
  <button
    className="back-dashboard-btn"
    onClick={() => navigate("/dashboard")}
  >
    ← Back to Dashboard
  </button>
</div>

<p className="total-fines">
  Total Fines: {fines.length}
</p>

      <div className="fines-table-container">
  <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Borrowing ID</th>
            <th>Amount</th>
            <th>Paid</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {fines.map((fine) => (
            <tr key={fine.id}>
              <td>{fine.id}</td>
              <td>{fine.borrowingId}</td>
              <td>₹{fine.amount}</td>
              <td>
  <span className={fine.paid ? "paid-status" : "unpaid-status"}>
    {fine.paid ? "Paid" : "Unpaid"}
  </span>
</td>
              <td>
                 {!fine.paid && (
                <button className="pay-fine-btn" onClick={() => payFine(fine.id)} > Pay Fine </button>
                )}
            </td>
            </tr>
          ))}
        </tbody>
      </table>
      </div>
    </div>
  );
}

export default Fines;