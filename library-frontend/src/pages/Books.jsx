import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import "../styles/Books.css";
import { Link } from "react-router-dom";
import RoleBased from "../components/RoleBased";


function Books() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("");
  const [page, setPage] = useState(0);
const [totalPages, setTotalPages] = useState(0);
const [totalBooks, setTotalBooks] = useState(0);

  const navigate = useNavigate();

 useEffect(() => {
  getBooks();
}, [page]);

 const getBooks = async () => {
  try {
    const response = await api.get("/books/page", {
      params: {
        page: page,
        size: 5,
      },
    });

    setBooks(response.data.content);
    setTotalPages(response.data.totalPages);
    setTotalBooks(response.data.totalElements);
  } catch (error) {
    console.log(error);
    setError("Failed to load books.");
  } finally {
    setLoading(false);
  }
};

const searchBooks = async () => {
  try {
    if (search.trim() === "" && category.trim() === "") {
      setPage(0);
      return;
    }

    const response = await api.get("/books/search/page", {
      params: {
        title: search.trim() || null,
        category: category.trim() || null,
        page: 0,
        size: 5,
        sortBy: "id",
        direction: "asc",
      },
    });

    setBooks(response.data.content);
    setTotalPages(response.data.totalPages);
    setTotalBooks(response.data.totalElements);
    setPage(0);

  } catch (error) {
    console.log(error);
    setError("Failed to search books.");
  }
};

const deleteBook = async (id) => {
  const confirmDelete = window.confirm(
    "Are you sure you want to delete this book?"
  );

  if (!confirmDelete) {
    return;
  }

  try {
    await api.delete(`/books/${id}`);

    setBooks(books.filter((book) => book.id !== id));
  } catch (error) {
  console.log(error);

  if (error.response && error.response.status === 403) {
    setError("You do not have permission to delete books.");
  } else {
    setError("Failed to delete book.");
  }
}
};

  if (loading) {
    return <h2>Loading books...</h2>;
  }

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div className="books-page">

     <div className="books-header">

  <h1>Books</h1>

  <div className="book-actions">

    <button
      className="back-dashboard-btn"
      onClick={() => navigate("/dashboard")}
    >
      ← Back to Dashboard
    </button>

    <RoleBased role="ADMIN">
      <Link to="/books/add" className="add-book-btn">
        + Add Book
      </Link>
    </RoleBased>

  </div>

</div>
     <div className="books-search">

  <input
    type="text"
    placeholder="Search by title..."
    value={search}
    onChange={(e) => {
      const value = e.target.value;
      setSearch(value);

      if (value.trim() === "" && category.trim() === "") {
        setPage(0);
      }
    }}
  />

  <input
    type="text"
    placeholder="Search by category..."
    value={category}
    onChange={(e) => {
      const value = e.target.value;
      setCategory(value);

      if (value.trim() === "" && search.trim() === "") {
        setPage(0);
      }
    }}
  />

  <button onClick={searchBooks}>
    Search
  </button>

</div>
      <p>Total Books: {totalBooks}</p>

      <div className="books-table-container">

        <table>

          <thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Category</th>
              <th>ISBN</th>
              <th>Price</th>
              <th>Available</th>
              <th>Actions</th>
            </tr>
          </thead>

         <tbody>

  {books.length === 0 ? (
    <tr>
      <td colSpan="7">
        No books found.
      </td>
    </tr>
  ) : (
    books.map((book) => (
      <tr key={book.id}>

        <td>{book.id}</td>
        <td>{book.title}</td>
        <td>{book.category}</td>
        <td>{book.isbn}</td>
        <td>₹{book.price}</td>
        <td>{book.availableQuantity}</td>

        <td>
          <RoleBased role="ADMIN">

            <button onClick={() => navigate(`/books/edit/${book.id}`)}>
              Edit
            </button>

            <button onClick={() => deleteBook(book.id)}>
              Delete
            </button>

          </RoleBased>
        </td>

      </tr>
    ))
  )}

</tbody>
        </table>

      </div>

   <div className="pagination">

  <button
    onClick={() => setPage(page - 1)}
    disabled={page === 0}
  >
    Previous
  </button>

  {Array.from({ length: totalPages }, (_, index) => (
    <button
      key={index}
      onClick={() => setPage(index)}
      disabled={page === index}
    >
      {index + 1}
    </button>
  ))}

  <button
    onClick={() => setPage(page + 1)}
    disabled={page === totalPages - 1}
  >
    Next
  </button>

</div>
    </div>
  );
}

export default Books;