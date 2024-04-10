package minesweeper
import minesweeper.*
import polyglot.OptionToOptional
import util.Sequences.Sequence
import util.Optionals.Optional as ScalaOptional

import java.util
import java.util.Optional
import scala.annotation.tailrec
class LogicsImpl(private val size: Int, private val mines: Int) extends Logics:
  private def CollectionToSequence[E](coll: util.Collection[E]): Sequence[E] =
    var s: Sequence[E] = Sequence.empty
    coll.forEach(e => s = s concat Sequence(e))
    return s

  extension (integer: Integer) private def toInt: Int = integer

  private val grid: Grid = SquareGrid(size)
  private val mineSeq: Sequence[(Int, Int)] =
    CollectionToSequence(RandomMineGenerator(grid, mines).generateMines()).map(p => (p.getX, p.getY))
  private var flagSeq: Sequence[(Int, Int)] = Sequence.empty

  override def cell(pos: Pair[Integer, Integer]): Optional[Integer] = grid.getCell(pos)
  override def victory(): Boolean =
    val remainingCells = CollectionToSequence(grid.allCells().entrySet())
      .filter(_.getValue.isEmpty)
      .map(c => (c.getKey.getX.toInt, c.getKey.getY.toInt))
    remainingCells.size == mines && remainingCells.containsAll(mineSeq)
  override def isMine(pos: Pair[Integer, Integer]): Boolean = mineSeq.contains((pos.getX, pos.getY))
  override def isFlag(pos: Pair[Integer, Integer]): Boolean = flagSeq.contains((pos.getX, pos.getY))
  override def toggleFlag(pos: Pair[Integer, Integer]): Unit =
    if !this.grid.isInside(pos) then
      throw new ArrayIndexOutOfBoundsException("Cannot place flag outside the grid")
    else
      if isFlag(pos) then
        flagSeq = flagSeq.filter(pos.getX != _ && pos.getY != _)
      else
        flagSeq = flagSeq.concat(Sequence((pos.getX, pos.getY)))

  override def hit(pos: Pair[Integer, Integer]): Boolean =
    if cell(pos).isEmpty then
      val d = calculateDistance(pos)
      grid.setCell(pos, d)
      if d == 0 then
        CollectionToSequence(grid.neighbors(pos).keySet()).forEach(hit)
    isMine(pos)

  private def calculateDistance(pos: Pair[Integer, Integer]): Int =
    CollectionToSequence(grid.neighbors(pos).keySet()).filter(isMine).size