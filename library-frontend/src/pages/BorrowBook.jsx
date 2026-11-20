import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import "../styles/BorrowBook.css";

function BorrowBook() {
  const [memberId, setMemberId] = useState("");
  const [bookId, setBookId] = useState("");
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const borrowing = {
      memberId: Number(memberId),
      bookId: Number(bookId)
    };

    try {
      await api.post("/borrowings", borrowing);

      setMessage("Book borrowed successfully!");

setTimeout(() => {
  const role = localStorage.getItem("role");

  if (role === "ADMIN") {
    navigate("/borrowings");
  } else {
    navigate("/dashboard");
  }
}, 1000);

   } catch (error) {
  console.log(error);

  if (error.response && error.response.data) {
    const errors = error.response.data;

    if (typeof errors === "object") {
      const messages = Object.values(errors);
      setMessage(messages.join(" • "));
    } else {
      setMessage(errors);
    }
  } else {
    setMessage("Failed to borrow book.");
  }
}
  };

 return (
  <div className="borrow-book-page">

    <div className="borrow-book-container">

      <h1>Borrow Book</h1>

      <form onSubmit={handleSubmit}>

    <div className="borrow-form-group">
  <label>Member ID:</label>
  <input
    type="number"
    value={memberId}
    onChange={(e) => setMemberId(e.target.value)}
    required
  />
</div>

<div className="borrow-form-group">
  <label>Book ID:</label>
  <input
    type="number"
    value={bookId}
    onChange={(e) => setBookId(e.target.value)}
    required
  />
</div>

        <button type="submit">
          Borrow Book
        </button>

     </form>

{message && (
  <p className="borrow-message">{message}</p>
)}

</div>
</div>
  );
}

export default BorrowBook;