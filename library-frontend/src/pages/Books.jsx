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
  const [pageSize, setPageSize] = useState(100);
  const [pageInput, setPageInput] = useState("1");
  const [appliedSearch, setAppliedSearch] = useState("");
  const [appliedCategory, setAppliedCategory] = useState("");
  const [totalPages, setTotalPages] = useState(0);
  const [totalBooks, setTotalBooks] = useState(0);

  const navigate = useNavigate();

 useEffect(() => {
  let isCurrentRequest = true;

  const loadBooks = async () => {
    try {
      const hasFilters = appliedSearch.trim() !== "" || appliedCategory.trim() !== "";
      const response = await api.get(
        hasFilters ? "/books/search/page" : "/books/page",
        {
          params: {
            page,
            size: pageSize,
            title: appliedSearch.trim() || null,
            category: appliedCategory.trim() || null,
            sortBy: "id",
            direction: "asc",
          },
        }
      );

      if (!isCurrentRequest) {
        return;
      }

      setBooks(response.data.content);
      setTotalPages(response.data.totalPages);
      setTotalBooks(response.data.totalElements);
    } catch (error) {
      if (!isCurrentRequest) {
        return;
      }

      console.log(error);
      setError("Failed to load books.");
    } finally {
      if (isCurrentRequest) {
        setLoading(false);
      }
    }
  };

  loadBooks();

  return () => {
    isCurrentRequest = false;
  };
}, [page, pageSize, appliedSearch, appliedCategory]);

const searchBooks = () => {
  setPage(0);
  setPageInput("1");
  setAppliedSearch(search);
  setAppliedCategory(category);
};

const pageButtonCount = 5;
const firstPageButton = Math.max(
  0,
  Math.min(page - Math.floor(pageButtonCount / 2), totalPages - pageButtonCount)
);
const visiblePageNumbers = Array.from(
  { length: Math.min(pageButtonCount, totalPages) },
  (_, index) => firstPageButton + index
);

const changePage = (nextPage) => {
  setPage(nextPage);
  setPageInput(String(nextPage + 1));
};

const goToPage = () => {
  const requestedPage = Number(pageInput);

  if (Number.isInteger(requestedPage) && requestedPage >= 1 && requestedPage <= totalPages) {
    changePage(requestedPage - 1);
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

      <label className="books-page-size">
        Books per page:
        <select
          value={pageSize}
          onChange={(event) => {
            setPageSize(Number(event.target.value));
            changePage(0);
          }}
        >
          <option value="20">20</option>
          <option value="50">50</option>
          <option value="100">100</option>
        </select>
      </label>

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
    onClick={() => changePage(page - 1)}
    disabled={page === 0}
  >
    Previous
  </button>

  {firstPageButton > 0 && <span>...</span>}

  {visiblePageNumbers.map((pageNumber) => (
    <button
      key={pageNumber}
      onClick={() => changePage(pageNumber)}
      disabled={page === pageNumber}
      aria-current={page === pageNumber ? "page" : undefined}
    >
      {pageNumber + 1}
    </button>
  ))}

  {firstPageButton + visiblePageNumbers.length < totalPages && <span>...</span>}

  <span>Page {page + 1} of {totalPages}</span>

  <input
    type="number"
    min="1"
    max={totalPages}
    value={pageInput}
    aria-label="Page number"
    onChange={(event) => setPageInput(event.target.value)}
  />
  <button onClick={goToPage} disabled={totalPages === 0}>Go</button>

  <button
    onClick={() => changePage(page + 1)}
    disabled={page >= totalPages - 1}
  >
    Next
  </button>

</div>
    </div>
  );
}

export default Books;