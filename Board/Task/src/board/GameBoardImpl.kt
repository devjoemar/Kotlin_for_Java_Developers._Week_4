package board

/**
 * Represents a game board that extends the functionality of a square board by allowing
 * values of a generic type T to be associated with each cell. This class enables the storage,
 * retrieval, and manipulation of data within the cells, supporting various game logic.
 *
 * @param T the type of values that can be stored in each cell of the game board.
 * @property width the size of one side of the square game board, indicating how many cells
 * there are per row and column.
 */
class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {
    /**
     * A map that associates each cell on the board with its stored value. The map allows for
     * quick lookup, update, and retrieval of cell values. Cells without explicitly set values
     * default to null, indicating they are empty or unassigned.
     */
    private val cellValues = mutableMapOf<Cell, T?>().withDefault { null }

    /**
     * Retrieves the value associated with a given cell. If no value has been set for the cell,
     * null is returned, indicating the cell is empty.
     *
     * @param cell the cell whose value is to be retrieved.
     * @return the value associated with the cell, or null if the cell is empty.
     */
    override fun get(cell: Cell): T? = cellValues.getValue(cell)

    /**
     * Associates a value with a given cell on the board. If the value is null, it effectively
     * clears the cell's value, marking it as empty.
     *
     * @param cell the cell whose value is to be set.
     * @param value the value to associate with the cell, or null to clear the cell's value.
     */
    override fun set(cell: Cell, value: T?) {
        cellValues[cell] = value
    }

    /**
     * Filters the cells on the board based on a predicate applied to their values. This method
     * is useful for finding cells that match certain criteria.
     *
     * @param predicate a function that tests each cell's value.
     * @return a collection of cells for which the predicate returns true.
     */
    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
            getAllCells().filter { cell -> predicate(get(cell)) }

    /**
     * Finds the first cell on the board that matches a given predicate applied to its value.
     *
     * @param predicate a function that tests each cell's value.
     * @return the first cell for which the predicate returns true, or null if no such cell is found.
     */
    override fun find(predicate: (T?) -> Boolean): Cell? =
            getAllCells().find { cell -> predicate(get(cell)) }

    /**
     * Checks if any cell on the board matches a given predicate applied to its value.
     *
     * @param predicate a function that tests each cell's value.
     * @return true if at least one cell matches the predicate, false otherwise.
     */
    override fun any(predicate: (T?) -> Boolean): Boolean =
            getAllCells().any { cell -> predicate(get(cell)) }

    /**
     * Checks if all cells on the board match a given predicate applied to their values.
     *
     * @param predicate a function that tests each cell's value.
     * @return true if all cells match the predicate, false otherwise.
     */
    override fun all(predicate: (T?) -> Boolean): Boolean =
            getAllCells().all { cell -> predicate(get(cell)) }
}
