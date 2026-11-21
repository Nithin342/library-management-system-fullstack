import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";

function MemberBorrowings() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [borrowings, setBorrowings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    getBorrowings();
  }, []);

  const getBorrowings = async () => {
    try {
      const response = await api.get(`/members/${id}/borrowings`);

      setBorrowings(response.data);
    } catch (error) {
      console.log(error);
      setError("Failed to load member borrowings.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <h2>Loading borrowings...</h2>;
  }

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div>
      <button onClick={() => navigate("/members")}>
        Back to Members
      </button>

      <h1>Member Borrowings</h1>

      <p>Total Borrowings: {borrowings.length}</p>

      {borrowings.length === 0 ? (
        <p>This member has no borrowings.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Book ID</th>
              <th>Book Name</th>
              <th>Borrow Date</th>
              <th>Due Date</th>
              <th>Return Date</th>
              <th>Status</th>
            </tr>
          </thead>

          <tbody>
            {borrowings.map((borrowing) => (
              <tr key={borrowing.id}>
                <td>{borrowing.id}</td>
                <td>{borrowing.bookId}</td>
                <td>{borrowing.bookTitle}</td>
                <td>{borrowing.borrowDate}</td>
                <td>{borrowing.dueDate}</td>
                <td>{borrowing.returnDate || "-"}</td>
                <td>{borrowing.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default MemberBorrowings;