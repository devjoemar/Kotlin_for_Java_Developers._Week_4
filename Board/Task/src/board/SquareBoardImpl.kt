package board

/**
 * Implements a square board for a game, where each position on the board is represented by a cell.
 * The board is square, meaning its width and height are equal, and it's composed of a grid of cells.
 * Each cell is identified by its row and column numbers, starting from 1. This class provides methods to
 * access cells directly, retrieve rows or columns of cells, and determine the neighboring cell in a given direction.
 *
 * @property width the size of one side of the square board, indicating how many cells there are per row and column.
 */
open class SquareBoardImpl(override val width: Int) : SquareBoard {

    /**
     * Initializes the board with a grid of cells. The grid is represented as a two-dimensional array,
     * where each cell is identified by its unique row and column combination. The cells are initialized
     * to reflect a 1-based indexing system used throughout this class.
     */
    private val cells = Array(width) { row ->
        Array(width) { col -> Cell(row + 1, col + 1) }
    }

    /**
     * Returns the cell at the specified position if it exists; otherwise, returns null. This method
     * is useful for safely accessing cells without risking an out-of-bounds exception.
     *
     * @param i the 1-based row index of the desired cell.
     * @param j the 1-based column index of the desired cell.
     * @return the cell at the given position, or null if the position is outside the bounds of the board.
     */
    override fun getCellOrNull(i: Int, j: Int): Cell? {
        val rowIndex = i - 1 // Adjusting i from 1-based to 0-based index
        val colIndex = j - 1 // Adjusting j from 1-based to 0-based index
        return if (rowIndex in cells.indices && colIndex in cells[rowIndex].indices) {
            cells[rowIndex][colIndex]
        } else null
    }

    /**
     * Returns the cell at the specified position, throwing an IllegalArgumentException if the position
     * is outside the bounds of the board. This method should be used when you're confident the cell exists
     * and want to avoid handling a null result.
     *
     * @param i the 1-based row index of the desired cell.
     * @param j the 1-based column index of the desired cell.
     * @return the cell at the given position.
     * @throws IllegalArgumentException if the specified position is outside the bounds of the board.
     */
    override fun getCell(i: Int, j: Int): Cell {
        val rowIndex = i - 1 // Adjusting i from 1-based to 0-based index
        val colIndex = j - 1 // Adjusting j from 1-based to 0-based index
        if (rowIndex !in cells.indices || colIndex !in cells[rowIndex].indices) {
            throw IllegalArgumentException("Invalid cell position: ($i, $j)")
        }
        return cells[rowIndex][colIndex]
    }


    /**
     * Retrieves a collection of all cells on the board, in no particular order. This method is useful
     * for iterating over every cell, for example, when initializing the board or applying a function to each cell.
     *
     * @return a collection containing all cells of the board.
     */
    override fun getAllCells(): Collection<Cell> =
        cells.flatten()

    /**
     * Retrieves a list of cells that constitute a specific row on the board. The row is determined by the
     * 1-based row index, and the range of columns to include is specified by jRange. This flexibility allows
     * for retrieving partial rows or rows in reverse order.
     *
     * @param i the 1-based row index.
     * @param jRange the range of 1-based column indices to include in the result.
     * @return a list of cells from the specified row within the given column range.
     */
    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
        jRange.mapNotNull { j -> getCellOrNull(i, j) }

    /**
     * Retrieves a list of cells that constitute a specific column on the board. Similar to getRow, but for columns.
     * The column is specified by the 1-based column index, and the range of rows to include is given by iRange.
     *
     * @param iRange the range of 1-based row indices to include in the result.
     * @param j the 1-based column index.
     * @return a list of cells from the specified column within the given row range.
     */
    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
        iRange.mapNotNull { i -> getCellOrNull(i, j) }

    /**
     * Determines the neighboring cell of a given cell in a specified direction. This method allows for easily
     * finding adjacent cells without manually calculating their positions. If the neighbor is outside the board's
     * boundaries, null is returned.
     *
     * @param direction the direction from the current cell to the neighbor (e.g., UP, DOWN, LEFT, RIGHT).
     * @return the neighboring cell in the specified direction, or null if the neighbor is outside the board's bounds.
     */
    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when (direction) {
            Direction.UP -> getCellOrNull(i - 1, j)
            Direction.DOWN -> getCellOrNull(i + 1, j)
            Direction.LEFT -> getCellOrNull(i, j - 1)
            Direction.RIGHT -> getCellOrNull(i, j + 1)
        }
}