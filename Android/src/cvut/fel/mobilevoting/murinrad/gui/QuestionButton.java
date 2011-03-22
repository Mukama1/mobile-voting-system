package cvut.fel.mobilevoting.murinrad.gui;



import java.util.ArrayList;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import cvut.fel.mobilevoting.murinad.datacontainers.QuestionData;
import cvut.fel.mobilevoting.murinrad.QuestionsView;
import cvut.fel.mobilevoting.murinrad.R;
import cvut.fel.mobilevoting.murinrad.R.string;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuestionButton extends DefaultButton {
	private QuestionData qData;
	QuestionsView parent;

	public QuestionButton(final Context context, final QuestionData qData,
			final QuestionsView parent) {
		super(context, qData.getText());
		this.qData = qData;
		this.parent = parent;
	}

	void showChoices() {
		final CharSequence[] cs = qData.getAnswers().toArray(
				new CharSequence[qData.getAnswers().size()]);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.answerPick));
		builder.setItems(cs, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(context.getApplicationContext(),
						qData.getAnswers().get(item), Toast.LENGTH_SHORT)
						.show();
				qData.setAnswer(item);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	void prepForSending() {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(parent.getString(R.string.confirmSendDialog))
				.setCancelable(true)
				.setPositiveButton(parent.getString(R.string.YES),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								try {
									passToSend();
								} catch (Exception ex) {

								}
							}
						})
				.setNegativeButton(R.string.NO,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();

	}

	void passToSend() throws ParserConfigurationException,
			FactoryConfigurationError {
		ArrayList<QuestionData> list = new ArrayList<QuestionData>();
		list.add(qData);
		parent.sendToServer(list);

	}

	@Override
	public void onClickAction() {
		showChoices();

	}

	@Override
	public void onLongClickAction() {
		prepForSending();

	}

}
