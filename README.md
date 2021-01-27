# Game of Three

Game of Three is a turn based two player game. When a player starts, it incepts a random (whole) number and sends it to the second
player as an approach of starting the game. The receiving player can now always choose between adding one of {-1, 0, 1} to get to a number that is divisible by 3.
Divide it by three. The resulting whole number is then sent back to the original sender.The same rules are applied until one player reaches the number 1(after the division).


## Requirements

1. Java - 1.8.x or higher

2. Gradle - 6.3.x or higher


## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/fatmaakinci/gameofthree.git
```

**2. Build and run the app using gradle**

```bash
cd gameofthree
gradle package
java -jar build/libs/gameofthree-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app directly without packaging it

```bash
gradle spring-boot:run
```

**3. Now you can play the game from:**

 http://localhost:8080
