import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../services/api";
import "../styles/AuthorBooks.css";

function AuthorBooks() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    getBooksByAuthor();
  }, []);

  const getBooksByAuthor = async () => {
    try {
      const response = await api.get(`/authors/${id}/books`);

      setBooks(response.data);
    } catch (error) {
      console.log(error);
      setError("Failed to load author's books.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <h2>Loading books...</h2>;
  }

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
  <div className="author-books-page">

    <h1>Author's Books</h1>

    <div className="author-books-actions">
      <button
        className="back-authors-btn"
        onClick={() => navigate("/authors")}
      >
        ← Back to Authors
      </button>
    </div>

    <p className="total-author-books">
      Total Books: {books.length}
    </p>

      {books.length === 0 ? (
        <p>This author has no books.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Category</th>
              <th>ISBN</th>
              <th>Price</th>
              <th>Available</th>
            </tr>
          </thead>

          <tbody>
            {books.map((book) => (
              <tr key={book.id}>
                <td>{book.id}</td>
                <td>{book.title}</td>
                <td>{book.category}</td>
                <td>{book.isbn}</td>
                <td>₹{book.price}</td>
                <td>{book.availableQuantity}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default AuthorBooks;