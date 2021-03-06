package view.widgets

import values.{ BooleanValue, UndefinedValue, Value }

import scalafx.scene.Node
import scalafx.scene.control.{ RadioButton, ToggleGroup }
import scalafx.scene.layout.HBox

class RadioWidget(
    trueText: String,
    falseText: String,
    protected val changeHandler: Option[Value => Unit]
) extends QLWidget {

  private val toggle = new ToggleGroup()
  private val trueButton = new RadioButton {
    text = trueText
    toggleGroup = toggle
    userData = BooleanValue(true) //Must be object (child of AnyRef) due to scalafx typing stuff.
  }
  private val falseButton = new RadioButton {
    text = falseText
    toggleGroup = toggle
    userData = BooleanValue(false)
  }

  private val buttons: HBox = new HBox {
    children = Seq(trueButton, falseButton)
  }

  toggle.selectedToggle.onChange {
    toggle.selectedToggle.value.getUserData match {
      case b: BooleanValue => changeHandler.foreach((f) => f(b))
      case _ => sys.error("Wrong type in radiobutton userdata field.")
    }
  }

  override def setValue(newVal: Value): Unit = newVal match {
    case BooleanValue(b) => trueButton.selected = b
    case UndefinedValue =>
      trueButton.selected = false
      falseButton.selected = false
    case v => sys.error(s"Incompatible value $v for Radio widget.")
  }

  override def displayNode: Node = buttons
}
