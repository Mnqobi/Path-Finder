# The Solution (Explained)

If the question is, how does one get from point A to point B then its simple,
via the shortest possible way! Being cognizant of the obstacles and the how to 
move comes secondary, and in that logic.

1. I had to figure out how to determine how far I am from my current point, 
thats when the mathematical formula for distance came into play.

2. Then I had to make sure that I keep an ordered record of the places I have to
visit, but only starting with the places closest to where I need to go, hence
the priority queue.

3. Ignore places I've already been to and got stuck, also ignore the walls.

4. I needed to keep a breadcrumb trail of how I got there so I can get back and
draw a map of the best route.

### You'll find the logic in the code follows this story :D