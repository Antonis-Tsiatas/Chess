package com.assignment.chess.dashboard.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ChessboardTest{

    @Test
    fun toSquareConvetsTopLefton8x8Board(){
        assertEquals("a8", Position(0,0).toSquare(8))
    }

    @Test
    fun toSquareConvertsMiddlePositionOn8x8Board(){
        assertEquals("b6", Position(2,1).toSquare(8))
    }
}