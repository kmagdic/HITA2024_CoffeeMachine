package zadatak2.booklibrary;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

        private Connection connection;
        private AuthorRepository authorRepository;

        BookRepository(Connection connection) {
            this.connection = connection;
        }

        public void setAuthorRepository(AuthorRepository authorRepository) {
            this.authorRepository = authorRepository;
        }

        public void createTable() {

            try {
                String sqlCreateTable = "CREATE TABLE IF NOT EXISTS book ( \n" +
                        "id integer PRIMARY KEY auto_increment, \n" +
                        "title varchar(200) NOT NULL,\n " +
                        "year_of_publication int NOT NULL,\n" +
                        "author_id int NOT NULL) \n";

                Statement st = connection.createStatement();
                st.execute(sqlCreateTable);

            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }


        public void insert(Book b) {

            String insertSql = "INSERT INTO book (title, year_of_publication, author_id) VALUES (?, ?, ?)";

            try {
                PreparedStatement ps = connection.prepareStatement(insertSql);
                ps.setString(1, b.getTitle());
                ps.setInt(2, b.getYearOfPublication());
                ps.setInt(3, b.getAuthor().getId());


                ps.executeUpdate();

            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
        }


        public List<Book> getList() {
            String sqlPrint = "SELECT * FROM book";
            List<Book> resultList = new ArrayList<>();

            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sqlPrint);

                while (rs.next()) {
                    Book b = new Book();
                    b.setId(rs.getInt("id"));
                    b.setTitle(rs.getString("title"));
                    b.setYearOfPublication(rs.getInt("year_of_publication"));
                    Author a = new Author();
                    a.setId(rs.getInt("author_id"));
                    b.setAuthor(a);


                    resultList.add(b);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return resultList;
        }
}
