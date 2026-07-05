# RNN vs LSTM vs GRU — Comprehensive Case Studies

A collection of 8 Jupyter notebooks comparing Vanilla RNN, LSTM, and GRU across different task types, plus 18 curated datasets organized by category. All notebooks are self-contained, use PyTorch, include hyperparameter tuning, and produce full three-way comparisons with metrics and visualizations.

---

## Notebooks

| # | Notebook | Dataset | Task Type | Key Concepts |
|---|----------|---------|-----------|--------------|
| 1 | `01_Univariate_Time_Series_Air_Passengers.ipynb` | Air Passengers (144 rows) | Univariate forecasting | Sliding window, naive baseline, sequence length ablation |
| 2 | `02_Multivariate_Time_Series_Jena_Climate.ipynb` | Jena Climate (420K rows) | Multivariate forecasting | Multi-horizon prediction, feature ablation, hourly resampling |
| 3 | `03_Financial_Time_Series_Stock_Prices.ipynb` | Stock Prices (4 tickers) | Financial forecasting | Why LSTMs fail at stocks, naive baseline lesson, directional accuracy |
| 4 | `04_Binary_Text_Classification_Spam_SMS.ipynb` | Spam SMS (5,574 msgs) | Binary text classification | Embedding + RNN, class imbalance, bidirectional comparison |
| 5 | `05_Multiclass_Text_Classification_AG_News.ipynb` | AG News (127K articles) | 4-class text classification | Word-level tokenization, TF-IDF baseline, layer depth ablation |
| 6 | `06_Character_Level_Text_Generation_Shakespeare.ipynb` | Shakespeare (1.1M chars) | Character-level generation | Temperature sampling, perplexity, text quality evolution |
| 7 | `07_Seq2Seq_Math_Equations.ipynb` | Math Equations (50K) | Encoder-decoder seq2seq | Teacher forcing decay, greedy decoding, generalization test |
| 8 | `08_Time_Series_Classification_ECG5000.ipynb` | ECG5000 (5K heartbeats) | Signal classification (5-class) | 1D-CNN baseline, t-SNE, saliency maps, class-weighted loss |

### How to Run

```bash
pip install -r requirements.txt
jupyter notebook
```

Each notebook follows a standard 10-section structure:
1. Introduction & Objectives
2. Environment Setup (SEED=42, GPU detection)
3. Data Loading & EDA
4. Preprocessing (train-only scaling, DataLoaders)
5. Model Architecture (RNN/LSTM/GRU unified factory)
6. Training Infrastructure (early stopping, gradient clipping)
7. Hyperparameter Tuning (random search)
8. Final Training & Comparison
9. Analysis & Insights
10. Conclusion

---

## Datasets

### 1. Time Series Forecasting
| Dataset | Source | Status |
|---------|--------|--------|
| Jena Climate | TensorFlow/Kaggle | Downloaded |
| Air Passengers | statsmodels | Downloaded |
| Electricity Consumption (UCI) | UCI ML Repo | Downloaded |
| Stock Prices (Yahoo Finance) | yfinance API | Downloaded (AAPL, GOOGL, MSFT, TSLA) |
| Sunspot Activity | statsmodels | Downloaded |

### 2. Text and NLP
| Dataset | Source | Status |
|---------|--------|--------|
| IMDB Reviews (50K) | Stanford AI | Downloaded |
| AG News | GitHub mirror | Downloaded |
| Shakespeare Text | TensorFlow | Downloaded |
| Spam SMS Collection | UCI ML Repo | Downloaded |
| Cornell Movie Dialogues | Cornell NLP | Downloaded |

### 3. Sequence-to-Sequence and Translation
| Dataset | Source | Status |
|---------|--------|--------|
| Multi30K (EN-DE) | GitHub | Downloaded |
| Tatoeba Sentence Pairs | manythings.org | Downloaded (HI, FR, MR) |
| Math Equation Dataset | Generated | Downloaded (50K equations) |

### 4. Speech and Audio
| Dataset | Source | Status |
|---------|--------|--------|
| Google Speech Commands | TensorFlow | Instructions only (1.5GB+) |
| LibriSpeech (mini) | OpenSLR | Instructions only (337MB+) |

### 5. Anomaly Detection and Signals
| Dataset | Source | Status |
|---------|--------|--------|
| ECG5000 | UCR Archive | Downloaded |
| NASA Bearing Dataset | NASA Prognostics | Instructions only (6GB+) |
| Credit Card Fraud | Kaggle | Instructions only (requires login) |

---

## Folder Structure

```
LSTM_Case_Studies-/
├── README.md
├── Time_Series_Forecasting/
│   ├── jena_climate/
│   ├── air_passengers/
│   ├── electricity_consumption/
│   ├── stock_prices/
│   └── sunspot_activity/
├── Text_and_NLP/
│   ├── imdb_reviews/
│   ├── ag_news/
│   ├── shakespeare_text/
│   ├── spam_sms/
│   └── cornell_movie_dialogues/
├── Seq2Seq_and_Translation/
│   ├── multi30k_en_de/
│   ├── tatoeba_sentence_pairs/
│   └── math_equation/
├── Speech_and_Audio/
│   ├── google_speech_commands/
│   └── librispeech_mini/
└── Anomaly_Detection_and_Signals/
    ├── ecg5000/
    ├── nasa_bearing/
    └── credit_card_fraud/
```

Each dataset folder contains a `DESCRIPTION.md` file with details about the dataset, its features, and LSTM use-case guidance.

---

## Requirements

- Python 3.10+
- PyTorch 2.0+
- See `requirements.txt` for full dependency list

## License

Datasets retain their original licenses. Notebooks are for educational purposes.
