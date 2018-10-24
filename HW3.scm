(define (deepSum L)
  (if
   (null? L) 0
   (if (list? (car L))
   (+ (deepSum (car L)) (deepSum (cdr L)))
   (+ (car L) (deepSum (cdr L))))
  ))
deepSum
(deepSum '(1 (2 3 4 ) (5) 6 7 (8 9 10) 11))
(deepSum '( () ))


(define (numbersToSum sum L)
  (let ((x 0))
  (cond
   ((null? L) x)
   ((> sum (car L)) (+ x (car L)
   (numbersToSum sum (cdr L))))
   ((= sum (car L))
    (numbersToSum sum (cdr L)))
   )))
;adds up all the numbers - honestly not exactly sure how to do this one
  
numbersToSum
(numbersToSum 10 '(1 4 5 10))
(numbersToSum 100 '(10 20 30 40))
(numbersToSum 30 '(5 4 6 10 4 2 1 5)) 
        

(define (isSorted L)
  (cond ((null? L) #t)
     ((eq? (length L) 1) #t)
        ((> (car (cdr L)) (car L))
        (isSorted (cdr l)))
        (else #f))
     )
isSorted
(isSorted '(1))
(isSorted '(1 4 5 6 10))
(isSorted '(1 3 6 5 10))

(define (mergeUnique2 L1 L2)
  (cond ((null? L1) L2)
        ((null? L2) L1)
        ((< (car L1) (car L2))
         (cons (car L1) (mergeUnique2 (cdr L1) L2)))
        ((= (car L1) (car L2))
         (mergeUnique2 (cdr L1) L2)) ;remove duplicates
        (else (cons (car L2) (mergeUnique2 L1 (cdr L2))))))

mergeUnique2
(mergeUnique2 '(4 6 7) '(3 5 7 8))
(mergeUnique2 '(1 5 7) '(2 5 7))
(mergeUnique2 '() '(3 5 7))

(define (fold f base L)
  (cond ((null? L) base)
        (else (f (car L) (fold f base (cdr L))))))
fold
(fold (lambda (x y) (if (< x y) y x )) 0 '(1 20 3 5 10))
(fold + 0 '(1 2 3))
(fold * 1 '(1 2 3 4))

        
(define (mergeUniqueN Ln)
  (cond((null? Ln) '())
       (else (fold append '() Ln))))
  ;appends but need to put in order
;assume no duplicates in lists
mergeUniqueN
(mergeUniqueN '())
(mergeUniqueN '((2 6) (1 4 5)))
(mergeUniqueN '((2 4 6 10) (1 3) (8 9)))


(define (matrixMap f M)
  (let nested ((next M))
    (cond ((null? M) next)
        ((pair? next) (map nested next))
        (else (f next)))))

matrixMap
(matrixMap (lambda (x) (* x x)) '((1 2) (3 4)) )
(matrixMap (lambda (x) (+ 1 x)) '((0 1 2) (3 4 5)) )


(define (avgOdd L)
  (cond ((null? L) 0)
        ((even? (car L))
         (avgOdd (cdr L)))
        ((odd? (car L))
         (cons (car L) (avgOdd (cdr L))))
  ))
avgOdd         
(avgOdd '(1 2 3 4 5))

(define (unzip L)
  (apply map list L))
;map puts the 1st elements into one list and the 2nd elements into another list
;apply appends those two lists together

unzip
(unzip '((1 2) (3 4) (5 6)))
(unzip '((1 “a”) (5 “b”) (8 “c”)))