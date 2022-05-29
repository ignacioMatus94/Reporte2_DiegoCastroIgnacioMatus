package CursoAI;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

import java.util.*;

public class Leviatan implements Bot {

    private static final Direction[] DIRECTIONS = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    private Snake snake = null;
    private Snake opponent = null;
    private Coordinate mazeSize = null;
    private Coordinate apple = null;

    private int maxProfundidad = 8;
    Integer MIN = Integer.MIN_VALUE;
    Integer MAX = Integer.MAX_VALUE;


    //minimax(0, 0, true, values, MIN, MAX));


    /**
     * Choose the direction
     * @param snake    Your snake's body with coordinates for each segment
     * @param opponent Opponent snake's body with coordinates for each segment
     * @param mazeSize Size of the board
     * @param manzana    Coordinate of an manzana
     * @return Direction of bot's move
     */

     //codigo de la competencia
    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate manzana)
    {

        //coordenadas de la cabeza de la serpiente
        Coordinate cabeza = snake.getHead();

        // find the backwards direction (afterhead), which we're not allowed to go towards
        Coordinate afterHeadNotFinal = null;

        //aqui el cuerpo empieza con dos cuadrados y una cabeza
        if (snake.body.size() >= 2)
        {
            Iterator<Coordinate> it = snake.body.iterator();
            it.next();
            afterHeadNotFinal = it.next();
        }

        final Coordinate afterHead = afterHeadNotFinal;

        //moverse en las direcciones permitidas
        Direction[] movimientosValidos = Arrays.stream(DIRECTIONS)
                .filter(d -> !cabeza.moveTo(d).equals(afterHead))
                .sorted().toArray(Direction[]::new);

        //1) no chocar con paredes, 2) no chocar con el cuerpo de la serpiente enemiga, 3) no chocar consigo mismo
        Direction[] noPerder = Arrays.stream(movimientosValidos)
                .filter(d -> cabeza.moveTo(d).inBounds(mazeSize))           // bordes de la tabla
                .filter(d -> !opponent.elements.contains(cabeza.moveTo(d))) // cuerpo del enemigo
                .filter(d -> !snake.elements.contains(cabeza.moveTo(d)))    // cuerpo de la serpiente
                .sorted().toArray(Direction[]::new); //renueva la serpiente


        // elige la direccion de la manzana con tal de que no choque con las paredes
        if (noPerder.length > 0)
        {
            //distancia entre cabeza y manzana
            Direction haciaLaManzana = direccionDesdeHacia(cabeza, manzana);

            for (Direction d : noPerder)
            {
                if (haciaLaManzana == d)
                {
                    //retorna el valor en direccion a la manzana
                    return haciaLaManzana;
                }
            }

            //si haciaLaManzana de nuestra serpeinte perdio en contra de la serpiente enemiga, genera algo al azar
            Random azar = new Random();

            //retorna el valor de noPerder.length aleatoriamente y genera nuevamente la acciones de la condicion
            return noPerder[azar.nextInt(noPerder.length)];
        }

        //retorna valores de los movimientos valores desde 0
        else return movimientosValidos[0];
    }

    /**
     * Set direction from one point to fin point
     * @param inicio point to begin
     * @param fin point to move
     * @return direction
     */

    //direccion desde el inicio hasta fin (otra ubicacion), codigo de la competencia
    public Direction direccionDesdeHacia(Coordinate inicio, Coordinate fin) {

        final Coordinate vector = new Coordinate(fin.x - inicio.x, fin.y - inicio.y);

        if (vector.x > 0)
        {
            return Direction.RIGHT;
        }
        else if (vector.x < 0)
        {
            return Direction.LEFT;
        }
        if (vector.y > 0)
        {
            return Direction.UP;
        }
        else if (vector.y < 0)
        {
            return Direction.DOWN;
        }

        for (Direction direccion : Direction.values())

            if (direccion.dx == vector.x && direccion.dy == vector.y)

                return direccion;

        return null;
    }



      //  aplicar el alfa beta en minixax con tal de que la serpiente se adelante al enemigo


  /*      public int minimax(int depth, int nodeIndex, Boolean maximizarJugador,int values[], int alpha, int beta)
        {

            // nodo hijo
            if (depth == 3)
                return values[nodeIndex];

            if (maximizarJugador)
            {
                int best = MIN;

                //nodo izquierda y derecha
                for (int i = 0; i < 2; i++)
                {
                    int val = minimax(depth + 1, nodeIndex 2 + i, false, values, alpha, beta);

                    best = Math.max(best, val);
                    alpha = Math.max(alpha, best);


                    if (beta <= alpha)
                        break;
                }
                return best;
            }
            else
            {
                int best = MAX;

                // Recur for left and
                // right children
                for (int i = 0; i < 2; i++)
                {

                    int val = minimax(depth + 1, nodeIndex * 2 + i, true, values, alpha, beta);

                    best = Math.min(best, val);
                    beta = Math.min(beta, best);

                    // Alpha Beta Pruning
                    if (beta <= alpha)
                        break;
                }
                return best;
            }
        }
*/










}
