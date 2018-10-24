fun revAppend ([],L) = L
	| revAppend (x::rest, L) = revAppend (rest, x::L);

fun reverse L = revAppend (L, []);

fun exists (x, []) = false
	| exists(x, y::z) = 
		if x = y then true
		else exists (x, z);

fun listUnion (x, y) = let
	fun loop (output, [], []) = output
		| loop (output, a::arest, b) = 
			if exists (a, output) then loop (output, arest, b)
			else loop (a::output, arest, b)
		| loop (output, a, b::brest) = 
			if exists (b, output) then loop (output, a, brest)
			else loop (y::output, a, brest);
	in loop([], x, y);
	

fun listIntersect (x, y) = let
	fun loop (output, []) = output
		| loop (output, y::rest) =
			if exists (a, y) then
				if exists (a, output) then loop (output, rest)
				else loop (a::output, rest)
			else loop (output, rest)
	in loop ([], x);

fun pairNleft x L = let
	fun group a [] [] = [[]]
		| group a (b::brest) [] = [[]]
		| group a [] (b::brest) = b::brest
		| group a (c::crest) (b::brest) = 
			if x > 0
				then group (a - 1) crest ((c::bcur)::brest)
			else group x (c::brest) ([]::b::brest)
	in
		if x > 0
			then group x (reverse(L)) [[]]
		else [[]];

fun pairNright x L = let
	fun group a [] [] = [[]]
		| group a (b::brest) [] = [[]]
		| group a [] (b::brest) = reverse(b)::brest
		| group a (c::crest) (b::brest) = 
			if a > 0
				then group (a - 1) crest 
					((c::b)::brest)
			else group x (c::crest) 
					([]::reverse(b)::brest)
	in
		if x > 0
			then reverse (group x L [[]])
		else [[]];

fun filterAux (pred, [], []) = []
	| filterAux (pred, [], output) = reverse output
	| filterAux (pred, x::rest, output) = 
		if pred x then filterAux (pred, rest, x::output)
		else filterAux (pred, rest, output);

fun filter pred L = filterAux (pred, L, []);

fun unitList L = pairNright (1 L);

datatype either = ImAString of string | ImAnInt of int;

fun eitherTree = Empty | Leaf of either | Node of eitherTree * eitherTree;

fun eitherSearch Empty x = false
	| eitherSearch (Node(1,rest)) x =
		(eitherSearch 1 x) orelse (eitherSearch rest x)
	| eitherSearch (Leaf(v)) x = 
		case v of ImAString v => false
			| ImAnInt v => x = v;

datatype 'a Tree = LEAF of 'a | NODE of ('a Tree) List;

fun treeToString pred (LEAF(v)) = pred v
	| treeToString pred (NODE(L)) = "(" ^ String.concat(List.map (treeToString pred) L) ^ ")";