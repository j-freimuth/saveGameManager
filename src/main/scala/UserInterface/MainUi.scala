package UserInterface

import scala.swing._

class MainUi {

  new Frame {

    title = "Hello World!"

    contents = new FlowPanel {
      contents += new Label("Launch rainbows:")
      contents += new Button("Click me!") {

        reactions += {
          case event.ButtonClicked(_) ⇒
            println("All the colours!")
        }
      }
    }

    pack()
    centerOnScreen()
    open()
  }
}
