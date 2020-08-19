import java.util.Random;

public class RandNumGenerator extends Random {
	// 唯一のインスタンスを保持（クラス変数）
	private static RandNumGenerator generator;
	// コンストラクタを private にし，外部からのインスタンス生成を禁止（ここがミソ）
	private RandNumGenerator() {}
	// インスタンスの取得メソッド（クラスメソッド）
	public static RandNumGenerator getInstance() {
		// インスタンスが存在しない場合（初回時），生成する
		if (generator == null)
			generator = new RandNumGenerator(); // クラス内部からは生成可能
		return generator;
		}
}
