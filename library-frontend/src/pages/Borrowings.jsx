import { useEffect, useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";
import "../styles/Borrowings.css";

function Borrowings() {
  const [borrowings, setBorrowings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    getBorrowings();
  }, []);

  const getBorrowings = async () => {
    try {
      const response = await api.get("/borrowings");

      setBorrowings(response.data);
    } catch (error) {
      console.log(error);
      setError("Failed to load borrowings.");
    } finally {
      setLoading(false);
    }
  };

 const returnBook = async (id) => {
  const confirmReturn = window.confirm(
    "Are you sure you want to return this book?"
  );

  if (!confirmReturn) {
    return;
  }

  try {
    await api.put(`/borrowings/${id}/return`);

    setBorrowings(
      borrowings.map((borrowing) =>
        borrowing.id === id
          ? { ...borrowing, status: "RETURNED" }
          : borrowing
      )
    );
  } catch (error) {
    console.log(error);
    setError("Failed to return book.");
  }
};  

  if (loading) {
    return <h2>Loading borrowings...</h2>;
  }

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div className="borrowings-page">
     <div className="borrowings-header">
  <h1>Borrowings</h1>
</div>

<div className="borrowing-actions">

  <button
    className="back-dashboard-btn"
    onClick={() => navigate("/dashboard")}
  >
    ← Back to Dashboard
  </button>

  <button
    className="borrow-book-btn"
    onClick={() => navigate("/borrowings/add")}
  >
    + Borrow Book
  </button>

</div>

<p className="total-borrowings">
  Total Borrowings: {borrowings.length}
</p>

      <div className="borrowings-table-container">
  <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Member ID</th>
            <th>Book ID</th>
            <th>Borrow Date</th>
            <th>Due Date</th>
            <th>Return Date</th>
            <th>Status</th>
            <th>Fine</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {borrowings.map((borrowing) => (
            <tr key={borrowing.id}>
              <td>{borrowing.id}</td>
              <td>{borrowing.memberId}</td>
              <td>{borrowing.bookId}</td>
              <td>{borrowing.borrowDate}</td>
              <td>{borrowing.dueDate}</td>
              <td>{borrowing.returnDate || "-"}</td>
              <td>{borrowing.status}</td>
              <td>₹{borrowing.fine}</td>
              <td>
                {borrowing.status === "BORROWED" && (
                <button className="return-book-btn" onClick={() => returnBook(borrowing.id)} > Return Book </button>
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

export default Borrowings;