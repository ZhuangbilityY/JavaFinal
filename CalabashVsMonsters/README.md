
# 葫芦娃大战蛇精

## 数据结构
	1. Individual package
		1. Individual
			1. implements Runnable 使用Runnable接口作为线程处理
			2. int CombatEffectiveness 
				1. 战斗力，每个个体赋予不同的战斗力，在对战时进行比例分配随机决定生死
				2. 在战力分配上，在生成个体的时候各自有一个随机战力范围，比如小怪是10-20
			3. Coordinates Position 位置信息，还有相似的一些属性
		2. Hero 
			1. extends Individual
			2. 好人阵营类
		3. Villian
			1. extends Individual
			2. 坏人阵营类
		4. CalabashBrothers
			1. extends Hero
			2. public enum Calabash { RED, ORG, YLW, GRN, CYN, BLU, PPL; } 葫芦种类
		5. Grandpa
			1. extends Hero
		6. Goblin
			1. extends Villian
		7. Scorpion
			1. extends Villian 
		8. Snake
			1. extends Villian
		
	2. Formations package
		1. Coordinates
			
		2. Formation
			1. implements Serializable 可序列化
			2. public enum Shape { Longsnake, Crane, Echelon, Yoke, Scale, Square, Crescent, Spearhead } 阵型类别
			3. public Vector<Coordinates> coordinatesList；
				1. 阵型对应的一系列坐标
		3. BattleField
			1. 战场，主要作为静态全局使用
			2. public static Hero[] heros;
			3. public static Villian[] villians;
			4. public static GridPane grid; 界面排布方式
	3. Game package
		1. Action
			1. implements Serializable 序列化
			2. 作为战斗行动信息来保存
		2. GameThread
			1. SequentialTransition AnimationList; 动画序列
			2. Vector<Action> ActionList; 战斗行动序列
	
	
## 方法实现
	1. Individual个体线程的run
	```
	public void run() {
        while(BattleField.hero_left > 0 && BattleField.villian_left > 0) {

            synchronized (BattleField.DuringMove) {
                if(!Alive) {
                    return;
                }
                if(BattleField.hero_left == 0 || BattleField.villian_left == 0)
                    break;
                Coordinates destination = getDestination();                          //目的位置
                if(BattleField.stance[destination.getX()][destination.getY()] == 0) {     //目的位置为空
                    updatePosition(destination);
                    addMoveAnimation();
                }
                else if(BattleField.stance[destination.getX()][destination.getY()] == 3 - MyStance) {  //目的位置为敌人
                    Individual enemy = BattleField.getIndividualOnSpot(destination);
                    addFightAnimation(enemy);
                    fight(enemy);
                }
                BattleField.updateStance();

             }
            Thread.yield();    //暂时放弃线程

        }
        if(!GameThread.Flag) {     //任务循环结束
            GameThread.Flag= true; 
            GameThread.InGame = false;
            GameThread.AnimationList.play();
        }

    }
	```
	
	
	2. 动画实现（以一个移动为例）
	```
	public static TranslateTransition MoveAnimation(Individual individual, int deviateX, int deviateY) {
        TranslateTransition move = new TranslateTransition(Duration.millis(500), individual.getIndividualImageView());  //平移动画

        move.setByX(deviateX * BattleField.boxWidth);    //设置平移量
        move.setByY(deviateY * BattleField.boxHeight);   

        move.setCycleCount(1);
        move.setAutoReverse(false);

        return move;
    }
	```
	
	
	3. 键盘响应
		1. 空格开始游戏，S保存游戏，L读取游戏
		2. 
		```
		        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                  @Override
                                  public void handle(KeyEvent event) {
                                      if(event.getCode() == KeyCode.SPACE) {
                                          if(!GameThread.InGame) {
                                              GameThread.InGame = true;
                                              game.startGame();
                                          }
                                      }
                                      else if(event.getCode() == KeyCode.L) {
                                          if(!GameThread.InGame) {
                                              FileChooser fileChooser = new FileChooser();
                                              File file = fileChooser.showOpenDialog(primaryStage);
                                              BattleField.initialize();
                                              GameThread.readGame(file);
                                              BattleField.setOriginScene();
                                              GameThread.AnimationList.play();
                                          }
                                      }
                                      else if(event.getCode() == KeyCode.S) {
                                          if(!GameThread.InGame) {
                                              FileChooser fileChooser = new FileChooser();
                                              File file = fileChooser.showSaveDialog(primaryStage);
                                              GameThread.saveGame(file);
                                              BattleField.initialize();
                                          }
                                      }

                                  }
                              }

				);
		```
	
## 	 其他
	1. maven由于和jdk10.0.2似乎有不适配的地方，所以代码中没有给出测试用例
